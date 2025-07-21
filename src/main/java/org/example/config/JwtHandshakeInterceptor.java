package org.example.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.List;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {

    private final JwtUtils jwtUtils;

    public JwtHandshakeInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        System.out.println("JwtHandshakeInterceptor cargandose");
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes) {
        System.out.println("En JWTHANDSHAKEINTERCEPTOR");
        System.out.println("COOKIES EN HEADERS?");
        Map<String, List<String>> headers = request.getHeaders();
        for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
            String key = entry.getKey();
            List<String> values = entry.getValue();
            System.out.println(key + ": " + values);
        }
        System.out.println(
                "Headers en shake: " + request.getHeaders() != null ? request.getHeaders().toString() : "No hay");
        System.out.println("clase request: " + request.getClass().getName());
        if (request instanceof ServletServerHttpRequest servletRequest) {
            System.out.println("ES INSTANCIA!");
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                System.out.println("HAY COOKIES BRO");
                for (Cookie cookie : cookies) {
                    System.out.println("COOKIE NAME: " + cookie.getName());
                    if ("jwt".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        System.out.println("JwtHandshakeInterceptor token: " + token);
                        if (jwtUtils.validateToken(token)) {
                            String username = jwtUtils.getUsername(token);
                            attributes.put("user", (Principal) () -> username);
                        }
                    }
                }
            } else {
                System.out.println("NO HAY COOKIES?");
            }
        }
        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception ex) {
    }
}
