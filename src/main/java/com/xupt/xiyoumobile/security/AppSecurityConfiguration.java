package com.xupt.xiyoumobile.security;

import com.xupt.xiyoumobile.security.jwt.JwtAuthenticationEntryPoint;
import com.xupt.xiyoumobile.security.jwt.JwtAuthenticationTokenFilter;
import com.xupt.xiyoumobile.security.provider.UserAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-27 09:32
 * <p>
 * SpringSecurity 实现基于角色的权限控制核心代码 ~
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint unauthorizedHandler;

    private final AccessDeniedHandler accessDeniedHandler;

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    private final AuthenticationFailureHandler authenticationFailureHandler;

    private final LogoutSuccessHandler logoutSuccessHandler;

    private final UserAuthenticationProvider userAuthenticationProvider;

    @Autowired
    public AppSecurityConfiguration(JwtAuthenticationEntryPoint unauthorizedHandler,
                                    @Qualifier("apiAccessDeniedHandler") AccessDeniedHandler accessDeniedHandler,
                                    @Qualifier("userLoginSuccessHandler") AuthenticationSuccessHandler authenticationSuccessHandler,
                                    @Qualifier("userLoginFailureHandler") AuthenticationFailureHandler authenticationFailureHandler,
                                    @Qualifier("userLogoutSuccessHandler") LogoutSuccessHandler logoutSuccessHandler,
                                    @Qualifier("userAuthenticationProvider") UserAuthenticationProvider userAuthenticationProvider) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
        this.userAuthenticationProvider = userAuthenticationProvider;
    }


    /**
     * 配置登录验证逻辑
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth){
        // 启用自定义登陆验证逻辑
        auth.authenticationProvider(userAuthenticationProvider);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .and()
                .cors()
                .and()
                // JWT不需要考虑csrf
                .csrf().disable()
                // 基于jwt的会话管理 不需要session管理
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .anyRequest().authenticated();


        // 禁用缓存
        http.headers().cacheControl();
        // 添加JWT filter
        http.addFilter(new JwtAuthenticationTokenFilter(authenticationManager()));
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/user/register",
                "/webjars/**",
                "/swagger-resources/**",
                "/v2/**",
                "/swagger-ui.html"
        );
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
