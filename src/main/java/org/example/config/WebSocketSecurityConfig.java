package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
import org.zuxercoding99.security.MessageExpressionAuthorizationManager;

@Configuration
public class WebSocketSecurityConfig {

        @Bean
        public AuthorizationManager<Message<?>> messageAuthorizationManager(
                        MessageMatcherDelegatingAuthorizationManager.Builder messages) {

                messages
                                // 1️⃣ Permitir heartbeats para que STOMP no se caiga
                                .simpTypeMatchers(SimpMessageType.HEARTBEAT).permitAll()

                                // 2️⃣ Todo lo demás de conexión requiere login
                                .simpTypeMatchers(SimpMessageType.CONNECT,
                                                SimpMessageType.DISCONNECT,
                                                SimpMessageType.UNSUBSCRIBE)
                                .authenticated()

                                // 3️⃣ Proteger los @MessageMapping
                                .simpDestMatchers("/app/**").authenticated()

                                // 4️⃣ Regla específica para grupos con SpEL
                                .simpSubscribeDestMatchers("/topic/group/{groupId}")
                                .access(new MessageExpressionAuthorizationManager(
                                                "isAuthenticated() and @groupService.isMember(principal.name, #groupId)"))

                                .simpSubscribeDestMatchers("/topic/users")
                                .access(new MessageExpressionAuthorizationManager(
                                                "isAuthenticated() and @userService.isUsernameUser(principal.name)"))

                                // 5️⃣ Autenticación obligatoria para cualquier otro /topic/**
                                .simpSubscribeDestMatchers("/topic/**").authenticated()

                                // 6️⃣ Colas privadas bajo /user/**
                                .simpSubscribeDestMatchers("/user/**").authenticated()

                                // 7️⃣ Denegar cualquier mensaje no cubierto
                                .anyMessage().denyAll();

                return messages.build();
        }

        @Bean
        public MessageExpressionAuthorizationManager messageAuthManager() {
                return new MessageExpressionAuthorizationManager("principal.name == #username");
        }

        @Bean
        public MessageExpressionAuthorizationManager isAuthAndGroupMember() {
                return new MessageExpressionAuthorizationManager(
                                "isAuthenticated() and @groupService.isMember(principal.name, #groupId)");
        }

        @Bean
        public MessageMatcherDelegatingAuthorizationManager.Builder messages() {
                return MessageMatcherDelegatingAuthorizationManager.builder();
        }

}