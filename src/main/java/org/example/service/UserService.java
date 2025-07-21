package org.example.service;

import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public User save(String name, String rawPassword) {
        User u = new User();
        u.setName(name);
        u.setPassword(encoder.encode(rawPassword));
        return repo.save(u);
    }
}