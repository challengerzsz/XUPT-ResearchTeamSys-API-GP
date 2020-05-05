package com.xupt.xiyoumobile.security.jwt;

import com.xupt.xiyoumobile.web.pojo.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-05 20:21
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final String APP_TOKEN_PREFIX = "Bearer ";

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JWTUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String appToken = request.getHeader(this.tokenHeader);

        if (StringUtils.isNotEmpty(appToken) && appToken.startsWith(APP_TOKEN_PREFIX)) {
            appToken = appToken.substring(APP_TOKEN_PREFIX.length());
        } else {
            // 不按规范,不允许通过验证
            appToken = null;
        }

        String username = jwtUtils.getUsernameFromToken(appToken);

        logger.info(String.format("Checking authentication for user %s.", username));

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user = jwtUtils.getUserFromToken(appToken);
            if (jwtUtils.validateToken(appToken, user)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info(String.format("Authenticated user %s, setting security context", username));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}
