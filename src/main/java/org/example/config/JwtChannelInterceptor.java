package org.example.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.security.Principal;

public class JwtChannelInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        Principal user = (Principal) accessor.getSessionAttributes().get("user");
        if (user != null) {
            accessor.setUser(user);
            System.out.println("JwtChannelInterceptor: Principal set for user: " + user.getName());
        } else {
            System.out.println("JwtChannelInterceptor: No Principal found.");
        }
        return message; // Always allow message, let WebSocketSecurityConfig handle authorization
    }
}