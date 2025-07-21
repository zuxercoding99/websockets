package org.example.controller;

import java.util.Map;

import org.example.model.User;
import org.example.payload.NewUserRequest;
import org.example.payload.LoginResponse;
import org.example.service.UserService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final SimpMessagingTemplate messaging;

    public UserController(UserService userService, SimpMessagingTemplate messaging) {
        this.userService = userService;
        this.messaging = messaging;
    }

    @PostMapping
    public User create(@RequestBody NewUserRequest r) {
        User u = userService.save(r.name(), "password"); // simplificado
        messaging.convertAndSend("/topic/users",
                Map.of("action", "CREATED", "user", Map.of("id", u.getId(), "name", u.getName())));
        return u;
    }
}
