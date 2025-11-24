package org.example.service;

import org.example.model.User;
import org.example.payload.NewUserRequest;
import org.example.repository.UserRepository;
import org.example.service.event.UserEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(NewUserRequest r) {
        User user = userRepository.save(new User(null, r.name(), passwordEncoder.encode(r.password())));
        eventPublisher.publishEvent(new UserEvent("CREATE", user));
        return user;
    }

    public boolean isUsernameUser(String username) {
        System.out.println("Username principal: " + username);
        return "user".equals(username);
    }
}