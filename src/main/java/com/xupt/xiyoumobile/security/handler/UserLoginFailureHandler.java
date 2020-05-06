package com.xupt.xiyoumobile.security.handler;

import com.alibaba.fastjson.JSONObject;
import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 15:39
 */
@Slf4j
@Component
public class UserLoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String errBody;

        if (exception instanceof UsernameNotFoundException) {
            log.info("[登陆失败] " + exception.getMessage());
            response.setStatus(200);
            errBody = JSONObject.toJSONString(
                    ApiResponse.createByErrorCodeMsg(ApiRspCode.USER_NOTFOUND.getCode(), exception.getMessage()));
        } else if (exception instanceof BadCredentialsException) {
            log.info("[登陆失败] " + exception.getMessage());
            response.setStatus(200);
            errBody = JSONObject.toJSONString(
                    ApiResponse.createByErrorCodeMsg(ApiRspCode.WRONG_PWD.getCode(), exception.getMessage()));
        } else {
            log.error("[权限校验异常] " + exception.getMessage());
            response.setStatus(500);
            errBody = JSONObject.toJSONString(ApiResponse.createByErrorMsg("服务端API内部异常"));
        }

        printWriter.write(errBody);
        printWriter.flush();
        printWriter.close();
    }
}
