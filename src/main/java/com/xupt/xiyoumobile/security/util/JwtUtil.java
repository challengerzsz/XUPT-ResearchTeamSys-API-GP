package com.xupt.xiyoumobile.security.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xupt.xiyoumobile.config.JwtConfig;
import com.xupt.xiyoumobile.security.entity.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.lang.Collections;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-05 21:06
 */
public class JwtUtil {

    public static String createJsonWebToken(SecurityUser user) {
        return Jwts.builder()
                .setId(user.getUserAccount())
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .claim("authorities", JSON.toJSONString(user.getAuthorities()))
                .setExpiration(new Date(System.currentTimeMillis() + JwtConfig.EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, JwtConfig.SECRET)
                .compact();
    }

    public static Principal authenticateJsonWebToken(String jwt) {

        UsernamePasswordAuthenticationToken authentication = null;
        // 解析JWT
        Claims claims = Jwts.parser()
                .setSigningKey(JwtConfig.SECRET)
                .parseClaimsJws(jwt)
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
            securityUser.setUserAccount(claims.getId());
            securityUser.setAuthorities(authorities);
            authentication = new UsernamePasswordAuthenticationToken(securityUser, userId, authorities);
        }
        return authentication;
    }
}