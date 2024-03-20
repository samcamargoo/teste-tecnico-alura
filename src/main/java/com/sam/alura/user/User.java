package com.sam.alura.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, length = 20)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime createdAt;

    @Deprecated
    public User() {
    }

    public User(String name, String username, String email, CleanPassword cleanPassword) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = cleanPassword.encode();
        this.createdAt = LocalDateTime.now();
    }
}
