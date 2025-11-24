package org.example.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public boolean isMember(String username, String groupId) {
        // Check database or logic
        return true; // Example
    }

    public void sendGroupMessage(String groupId, String message, String sender) {
        messagingTemplate.convertAndSend("/topic/group/" + groupId,
                Map.of("sender", sender, "message", message));
    }
}