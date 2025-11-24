package org.example.service.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleUserEvent(UserEvent event) {
        System.out.println("Enviando evento WebSocket para usuario: " + event.getUser().getName());
        messagingTemplate.convertAndSend("/topic/users", event);
    }
}