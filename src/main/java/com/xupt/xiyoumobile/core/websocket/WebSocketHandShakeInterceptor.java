package com.xupt.xiyoumobile.core.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 01:06
 */
@Slf4j
@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    private RedisTemplate redisTemplate;

    @Autowired
    public WebSocketHandShakeInterceptor(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 握手处理 鉴权过程
     *
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws HandshakeFailureException
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        log.info("A new websocket handshake is processing!");

//        ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
//        Long uid = Long.valueOf(httpRequest.getServletRequest().getParameter("uid"));
//        String token = httpRequest.getServletRequest().getParameter("token");
//        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(uid)) {
//            String tokenInRedis = (String) redisTemplate.opsForHash().get(UID_TOKEN_PREFIX, uid);
//            if (token.equals(tokenInRedis)) {
//                return true;
//            }
//        }

        // TODO: 2020-06-04 JWT authentication

        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
