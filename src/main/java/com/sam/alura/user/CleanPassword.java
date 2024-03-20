package com.sam.alura.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record CleanPassword(String password) {

    String encode() {
        return new BCryptPasswordEncoder().encode(password);
    }
}
