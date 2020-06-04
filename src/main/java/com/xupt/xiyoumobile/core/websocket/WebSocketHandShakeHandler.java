package com.xupt.xiyoumobile.core.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 01:05
 */
@Slf4j
@Component
public class WebSocketHandShakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        return (Principal) attributes.get("principal");
    }
}
