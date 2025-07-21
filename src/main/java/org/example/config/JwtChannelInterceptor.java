package org.example.config;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;

import java.security.Principal;

public class JwtChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("En JWTCHANNELINTERCEPTOR");
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        Principal user = (Principal) accessor.getSessionAttributes().get("user");

        if (user != null) {
            accessor.setUser(user);
        }

        return message;
    }
}