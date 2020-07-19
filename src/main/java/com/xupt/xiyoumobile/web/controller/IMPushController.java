package com.xupt.xiyoumobile.web.controller;

import com.xupt.xiyoumobile.common.ApiResponse;
import com.xupt.xiyoumobile.core.websocket.config.WebSocketBrokerConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * @author : zengshuaizhi
 * @date : 2020-05-14 23:44
 */
@Slf4j
@RestController
public class IMPushController {

    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    public IMPushController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/chat")
    @SendToUser("/private")
    public ApiResponse<String> chat(Principal principal, String msg) {
        simpMessagingTemplate.convertAndSendToUser(principal.getName(), "/private", msg);
        return ApiResponse.createBySuccessMsg("发送成功");
    }
}
