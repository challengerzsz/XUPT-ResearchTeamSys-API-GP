package com.xupt.xiyoumobile.security.handler;

import com.alibaba.fastjson.JSON;
import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.config.JwtConfig;
import com.xupt.xiyoumobile.security.entity.SecurityUser;
import com.xupt.xiyoumobile.security.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 15:54
 */
@Slf4j
@Component
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 组装JWT
        SecurityUser securityUser =  (SecurityUser) authentication.getPrincipal();
        StringBuilder token = new StringBuilder(JwtConfig.TOKEN_PREFIX);
        token.append(JwtUtil.createJsonWebToken(securityUser));

        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        PrintWriter printWriter = response.getWriter();
        String body = JSON.toJSONString(ApiResponse.createBySuccess("登录成功", token));
        printWriter.write(body);
        printWriter.flush();
        printWriter.close();
    }
}
