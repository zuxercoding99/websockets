package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // @Autowired
    // private WebSocketAuthInterceptor webSocketAuthInterceptor;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry reg) {
        reg.enableSimpleBroker("/topic");
        reg.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry reg) {
        reg.addEndpoint("/ws").setAllowedOriginPatterns("*").addInterceptors(new JwtHandshakeInterceptor(jwtUtils))
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor());
    }

}