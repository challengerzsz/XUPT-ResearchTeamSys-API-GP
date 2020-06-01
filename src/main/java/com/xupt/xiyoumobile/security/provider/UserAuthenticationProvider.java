package com.xupt.xiyoumobile.security.provider;

import com.xupt.xiyoumobile.security.entity.SecurityUser;
import com.xupt.xiyoumobile.security.user.AppUserDetailsService;
import com.xupt.xiyoumobile.web.entity.Role;
import com.xupt.xiyoumobile.web.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自定义权限校验 Provider
 *
 * @author : zengshuaizhi
 * @date : 2020-05-06 16:38
 */
@Slf4j
@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private AppUserDetailsService appUserDetailsService;

    private IUserService userService;

    @Autowired
    public UserAuthenticationProvider(AppUserDetailsService appUserDetailsService, IUserService userService) {
        this.appUserDetailsService = appUserDetailsService;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();
        // 查询用户是否存在
        SecurityUser securityUserInfo = appUserDetailsService.loadUserByUsername(userName);
        if (securityUserInfo == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        // 校验密码
        if (!new BCryptPasswordEncoder().matches(password, securityUserInfo.getPassword())) {
            throw new BadCredentialsException("密码错误");
        }

        // 角色集合
        Set<GrantedAuthority> authorities = new HashSet<>();
        // 查询用户角色
        List<Role> roleList = userService.selectRoleByUserId(securityUserInfo.getId());
        for (Role role : roleList) {
            // api注解校验需要使用roleName匹配 这里需要注意源码做权限校验的时候需要ROLE_前缀
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        }
        securityUserInfo.setAuthorities(authorities);
        // 进行登录
        return new UsernamePasswordAuthenticationToken(securityUserInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
