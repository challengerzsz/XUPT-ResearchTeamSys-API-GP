package com.xupt.xiyoumobile.core.websocket;

import com.xupt.xiyoumobile.config.JwtConfig;
import com.xupt.xiyoumobile.security.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeFailureException;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.ServletRequest;
import java.security.Principal;
import java.util.Map;

/**
 * @author : zengshuaizhi
 * @date : 2020-06-04 01:06
 */
@Slf4j
@Component
public class WebSocketHandShakeInterceptor implements HandshakeInterceptor {

    public static final String WEBSOCKET_JWT_KEY = "token";

    /**
     * 握手处理 websocket鉴权过程
     * websocket独立于http请求的鉴权过程
     * @param request
     * @param response
     * @param wsHandler
     * @param attributes
     * @return
     * @throws HandshakeFailureException
     */
    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {
        log.info("A new websocket handshake is processing!");

        ServletServerHttpRequest httpRequest = (ServletServerHttpRequest) request;
        String jsonWebToken = httpRequest.getServletRequest().getParameter(WEBSOCKET_JWT_KEY);
        if (null != jsonWebToken && jsonWebToken.startsWith(JwtConfig.TOKEN_PREFIX)) {
            try {
                // 截取JWT前缀
                String token = jsonWebToken.replace(JwtConfig.TOKEN_PREFIX, "");
                // 解析JWT
                Principal principal = JwtUtil.authenticateJsonWebToken(token);
                if (principal == null) {
                    log.warn("Someone is trying to connect im-server but not use jwt!");
                    return false;
                }
                attributes.put("principal", principal);
                return true;
            } catch (ExpiredJwtException e) {
                log.warn("[token expired websocket handshake failed!] " + e.getMessage());
                return false;
            } catch (Exception e) {
                log.info("[valid token websocket handshake failed!] " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {
    }
}
