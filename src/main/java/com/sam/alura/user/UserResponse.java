package com.sam.alura.user;

public record UserResponse(String name, String email, String role) {

    public UserResponse(ApplicationUser userEntity) {
        this(userEntity.getName(), userEntity.getEmail(), userEntity.getRole());
    }
}
