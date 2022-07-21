package com.seventeam.algoritmgameproject.config.stomp;

import com.sun.security.auth.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;
@Slf4j
public class CustomHandShaker extends DefaultHandshakeHandler {
    // connection header
    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        // 이건 추후 통일
        String uri = request.getURI().toString();
        final String username  = uri.split("=")[1];
        log.info("User with ID '{}' opened the page", username);
        return new UserPrincipal(username);
    }
}