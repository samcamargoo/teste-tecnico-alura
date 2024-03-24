package com.sam.alura.user;

public enum RoleType {
    ROLE_STUDENT("STUDENT"),
    ROLE_INSTRUCTOR("INSTRUCTOR"),
    ROLE_ADMIN("ADMIN");

    private String description;
    RoleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
