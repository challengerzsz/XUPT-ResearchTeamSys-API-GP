package com.xupt.xiyoumobile.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.common.ApiRspCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-05 20:33
 */
@Component
@Slf4j
public class ApiAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException {
        // 403 FORBIDDEN
        log.warn("403 FORBIDDEN: " + e.getMessage());
        response.setStatus(403);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        String body = JSON.toJSONString(ApiResponse.createByErrorCodeMsg(ApiRspCode.FORBIDDEN.getCode(),
                e.getMessage()));
        printWriter.write(body);
        printWriter.flush();
        printWriter.close();
    }
}
