package org.example.config;

public class WebSocketAuthInterceptor {

}
/*
 * import java.util.List;
 * 
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.messaging.Message;
 * import org.springframework.messaging.MessageChannel;
 * import org.springframework.messaging.simp.stomp.StompCommand;
 * import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
 * import org.springframework.messaging.support.ChannelInterceptor;
 * import org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken;
 * import org.springframework.security.core.authority.SimpleGrantedAuthority;
 * import org.springframework.stereotype.Component;
 * 
 * @Component
 * public class WebSocketAuthInterceptor implements ChannelInterceptor {
 * 
 * @Autowired
 * private JwtUtils jwtUtils;
 * 
 * static {
 * System.out.println("Cargando WebSocketAuthInterceptor");
 * }
 * 
 * @Override
 * public Message<?> preSend(Message<?> message, MessageChannel channel) {
 * System.out.println("En WebSocketAuthInterceptor");
 * StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
 * System.out.println("Command: " + accessor.getCommand());
 * System.out.println("Authorization header: " +
 * accessor.getFirstNativeHeader("Authorization"));
 * if (StompCommand.CONNECT.equals(accessor.getCommand())) {
 * String token = accessor.getFirstNativeHeader("Authorization");
 * System.out.println("Token before parse: " + token);
 * if (token != null && token.startsWith("Bearer ")) {
 * token = token.substring(7);
 * System.out.println("Token after substring: " + token);
 * if (jwtUtils.validateToken(token)) {
 * String username = jwtUtils.getUsername(token);
 * System.out.println("Token valid for user: " + username);
 * UsernamePasswordAuthenticationToken auth = new
 * UsernamePasswordAuthenticationToken(
 * username, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
 * accessor.setUser(auth);
 * } else {
 * System.out.println("Token no valido");
 * }
 * } else {
 * System.out.println("No Authorization header o no comienza con Bearer");
 * }
 * }
 * return message;
 * }
 * }
 */