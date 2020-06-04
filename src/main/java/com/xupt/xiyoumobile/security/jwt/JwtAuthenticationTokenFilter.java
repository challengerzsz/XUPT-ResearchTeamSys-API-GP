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
import org.springframework.security.core.Authentication;
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
import java.security.Principal;
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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 获取请求头中JWT的Token
        String tokenHeader = request.getHeader(JwtConfig.TOKEN_HEADER);
        if (null != tokenHeader && tokenHeader.startsWith(JwtConfig.TOKEN_PREFIX)) {
            try {
                // 截取JWT前缀
                String token = tokenHeader.replace(JwtConfig.TOKEN_PREFIX, "");
                // 解析JWT
                Principal principal = JwtUtil.authenticateJsonWebToken(token);
                if (principal != null) {
                    SecurityContextHolder.getContext().setAuthentication((Authentication) principal);
                }
            } catch (ExpiredJwtException e) {
                log.warn("[token expired] " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("[valid token] " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}
