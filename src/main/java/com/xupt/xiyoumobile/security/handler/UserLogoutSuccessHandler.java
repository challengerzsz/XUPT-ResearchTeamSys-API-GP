package com.xupt.xiyoumobile.security.handler;

import com.alibaba.fastjson.JSON;
import com.xupt.xiyoumobile.common.ApiResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-06 16:03
 */
@Component
public class UserLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");

        // 用户退出清空Security全局上下文中的用户权限属性
        SecurityContextHolder.clearContext();

        PrintWriter printWriter = response.getWriter();
        String body = JSON.toJSONString(ApiResponse.createBySuccessMsg("logout success!"));
        printWriter.write(body);
        printWriter.flush();
        printWriter.close();
    }
}
