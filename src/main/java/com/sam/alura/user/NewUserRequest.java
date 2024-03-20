package com.sam.alura.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record NewUserRequest(@NotBlank(message = "is required") String name,
                             @Pattern(regexp = "^[a-z]+$", message = "Invalid username")
                             @Length(max = 20, message = "length must be equal or less than 20")
                             String username,
                             @Email(message = "Invalid email")
                             @NotBlank(message = "is required")
                             String email,
                             @NotBlank(message = "is required")
                             String password) {

    public User toModel() {
        return new User(name, username, email, new CleanPassword(password));
    }
}
