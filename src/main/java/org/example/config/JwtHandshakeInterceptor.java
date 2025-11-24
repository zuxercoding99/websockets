package org.example.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;
import java.util.Map;

public class JwtHandshakeInterceptor implements HandshakeInterceptor {
    private final JwtUtils jwtUtils;

    public JwtHandshakeInterceptor(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Map<String, Object> attributes) {
        System.out.println("En JwtHandshakeInterceptor");

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            Cookie[] cookies = httpRequest.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        System.out.println("JwtHandshakeInterceptor token: " + token);
                        if (jwtUtils.validateToken(token)) {
                            String username = jwtUtils.getUsername(token);
                            attributes.put("user", (Principal) () -> username);
                            System.out.println("Principal set for user: " + username);
                        } else {
                            System.out.println("Token inválido, proceeding without Principal.");
                        }
                        return true; // Allow handshake even if token is invalid
                    }
                }
            }
            System.out.println("No JWT cookie found, proceeding without Principal.");
        }
        return true; // Allow handshake if no cookies or no JWT
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
            WebSocketHandler wsHandler, Exception ex) {
    }
}