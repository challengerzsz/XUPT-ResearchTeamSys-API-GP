package com.xupt.xiyoumobile.core.websocket.config;

import com.xupt.xiyoumobile.core.websocket.WebSocketHandShakeHandler;
import com.xupt.xiyoumobile.core.websocket.WebSocketHandShakeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 01:02
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfiguration implements WebSocketMessageBrokerConfigurer {

    private WebSocketHandShakeInterceptor webSocketHandShakeInterceptor;

    private WebSocketHandShakeHandler webSocketHandShakeHandler;

    @Autowired
    public WebSocketBrokerConfiguration(WebSocketHandShakeInterceptor webSocketHandShakeInterceptor, WebSocketHandShakeHandler webSocketHandShakeHandler) {
        this.webSocketHandShakeInterceptor = webSocketHandShakeInterceptor;
        this.webSocketHandShakeHandler = webSocketHandShakeHandler;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/im-server")
                .setHandshakeHandler(webSocketHandShakeHandler)
                .setAllowedOrigins("*").withSockJS()
                .setInterceptors(webSocketHandShakeInterceptor);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic/private", "/topic/public", "/topic/test");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

}
