package com.xupt.xiyoumobile.security.util;

import com.alibaba.fastjson.JSON;
import com.xupt.xiyoumobile.config.JwtConfig;
import com.xupt.xiyoumobile.security.entity.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

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
}
