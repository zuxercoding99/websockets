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
        String command = accessor.getCommand() != null ? accessor.getCommand().name() : null;
        System.out.println("JwtChannelInterceptor: Processing command: " + command);

        Principal user = (Principal) accessor.getSessionAttributes().get("user");

        switch (accessor.getCommand()) {
            case CONNECT:
            case SUBSCRIBE:
            case SEND:
                if (user == null) {
                    System.out.println("Bloqueado: sin usuario para comando " + accessor.getCommand());
                    return null; // bloquea el mensaje I dont know if it's better null than  throw new IllegalStateException
                } else {
                    System.out.println("Authenticated user for subscription: " + user.getName());
                    accessor.setUser(user); // lo pasa si está
                }
                break;
            default:
                break;
        }

        return message;
    }

    @Override
    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("JwtChannelInterceptor: Message sent: " + sent);
    }
}