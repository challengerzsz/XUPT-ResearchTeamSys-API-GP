package com.xupt.xiyoumobile.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xupt.xiyoumobile.exception.UserInfoException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author : zengshuaizhi
 * @date : 2020-03-26 19:59
 */
@Slf4j
//@Component
public class OverAllAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public static final String USER_ACCOUNT = "userAccount";
    public static final String PASSWORD = "password";


    public OverAllAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String requestBody = StreamUtils.copyToString(httpServletRequest.getInputStream(), Charset.forName("UTF-8"));


        logger.info("enter filter");
        String userAccount = null, password = null;
        if (!StringUtils.isBlank(requestBody)) {
            JSONObject object = JSON.parseObject(requestBody);
            userAccount = object.getString(USER_ACCOUNT);
            password = object.getString(PASSWORD);
        }

        if (StringUtils.isAllBlank(userAccount, password)) {
            // TODO: 2020-03-26 WholeContextExceptionHandler to resolve this situation

        }

        // spring security's token
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userAccount, password);
        return this.getAuthenticationManager().authenticate(token);
    }
}
