package org.example.service.event;

import org.example.model.User;

public class UserEvent {
    private final String action;
    private final User user;

    public UserEvent(String action, User user) {
        this.action = action;
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public User getUser() {
        return user;
    }
}