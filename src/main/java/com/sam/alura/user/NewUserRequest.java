package com.sam.alura.user;

import com.sam.alura.exceptions.AppException;
import com.sam.alura.shared.Unique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record NewUserRequest(@NotBlank(message = "is required") String name,
                             @Pattern(regexp = "^[a-z]+$", message = "Invalid username")
                             @Length(max = 20, message = "length must be equal or less than 20")
                             @Unique(fieldName = "username", domainClass = ApplicationUser.class)
                             String username,
                             @Email(message = "Invalid email")
                             @NotBlank(message = "is required")
                             @Unique(fieldName = "email", domainClass = ApplicationUser.class)
                             String email,
                             @NotBlank(message = "is required")
                             String password) {

    public ApplicationUser toModel(RoleRepository roleRepository) {
        Role role = roleRepository.findRoleByType(RoleType.ROLE_STUDENT).orElseThrow(() -> new AppException("role not found"));
        return new ApplicationUser(name, username, email, new CleanPassword(password), role);
    }
}
