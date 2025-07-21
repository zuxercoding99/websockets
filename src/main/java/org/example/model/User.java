package org.example.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue
    Long id;
    @Column(unique = true)
    String name;
    String password;
    // getters & setters
}