package com.xupt.xiyoumobile.security.jwt;

import com.alibaba.fastjson.JSONObject;
import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-05 20:25
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {

        System.out.println("Authentication failed：" + authException.getMessage());
        response.setStatus(401);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = JSONObject.toJSON(ApiResponse.createByErrorCodeMsg(ApiRspCode.UNAUTHORIZED.getCode(),
                authException.getMessage())).toString();
        printWriter.write(body);
        printWriter.flush();
        printWriter.close();
    }
}