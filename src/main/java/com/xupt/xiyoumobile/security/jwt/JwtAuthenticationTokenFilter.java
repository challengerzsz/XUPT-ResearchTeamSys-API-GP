package com.xupt.xiyoumobile.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.xupt.xiyoumobile.config.JwtConfig;
import com.xupt.xiyoumobile.security.entity.SecurityUser;
import com.xupt.xiyoumobile.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.lang.Collections;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-05 20:21
 */
@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends BasicAuthenticationFilter {


    public JwtAuthenticationTokenFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JwtConfig.TOKEN_HEADER);
        if (null != tokenHeader && tokenHeader.startsWith(JwtConfig.TOKEN_PREFIX)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.replace(JwtConfig.TOKEN_PREFIX, "");
                // 解析JWT
                Claims claims = Jwts.parser()
                        .setSigningKey(JwtConfig.SECRET)
                        .parseClaimsJws(token)
                        .getBody();
                // 获取用户名
                String username = claims.getSubject();
                String userId = claims.getId();
                if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(userId)) {
                    // 获取角色
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String authority = claims.get("authorities").toString();
                    if (!StringUtils.isEmpty(authority)) {
                        List<Map<String, String>> authorityMap = JSONObject.parseObject(authority, List.class);
                        for (Map<String, String> role : authorityMap) {
                            if (!Collections.isEmpty(role)) {
                                authorities.add(new SimpleGrantedAuthority(role.get("authority")));
                            }
                        }
                    }
                    //组装参数
                    SecurityUser securityUser = new SecurityUser();
                    securityUser.setUserName(claims.getSubject());
                    securityUser.setId(Long.parseLong(claims.getId()));
                    securityUser.setAuthorities(authorities);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(securityUser, userId, authorities);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (ExpiredJwtException e) {
                log.warn("[token expired] " + e.getMessage());
            } catch (Exception e) {
                log.info("[valid token] " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
