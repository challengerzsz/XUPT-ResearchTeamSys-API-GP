package com.xupt.xiyoumobile.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 22:01
 */
@Getter
@Component
public class JwtConfig {

    public static String SECRET;

    public static Integer EXPIRATION;

    public static String TOKEN_HEADER;

    public static String TOKEN_PREFIX;

    @Value("${jwt.secret}")
    public void setSECRET(String secret) {
        SECRET = secret;
    }

    @Value("${jwt.expiration}")
    public void setEXPIRATION(Integer expiration) {
        EXPIRATION = expiration;
    }

    @Value("${jwt.tokenHeader}")
    public void setTokenHeader(String tokenHeader) {
        TOKEN_HEADER = tokenHeader;
    }

    @Value("${jwt.tokenPrefix}")
    public void setTokenPrefix(String tokenPrefix) {
        TOKEN_PREFIX = tokenPrefix;
    }
}
