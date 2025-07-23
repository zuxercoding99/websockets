package org.example.config;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.security.Principal;

import java.util.Map;

/*
 * This ensures that only clients with a valid JWT can establish a WebSocket connection.
 */
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

        if (request instanceof ServletServerHttpRequest servletRequest) {
            HttpServletRequest httpRequest = servletRequest.getServletRequest();
            Cookie[] cookies = httpRequest.getCookies();

            if (cookies != null) {
                System.out.println("HAY COOKIES BRO");
                for (Cookie cookie : cookies) {
                    if ("jwt".equals(cookie.getName())) {
                        String token = cookie.getValue();
                        System.out.println("JwtHandshakeInterceptor token: " + token);
                        if (jwtUtils.validateToken(token)) {
                            String username = jwtUtils.getUsername(token);
                            attributes.put("user", (Principal) () -> username);
                            return true;
                        } else {
                            System.out.println("Token inválido.");
                            response.setStatusCode(HttpStatus.UNAUTHORIZED);
                            return false;
                        }
                    }
                }
            } else {
                System.out.println("NO HAY COOKIES?");
            }
        }
        System.out.println("Token no encontrado.");
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception ex) {
    }
}
