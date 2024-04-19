package com.sam.alura.shared;

import com.sam.alura.user.NewUserRequest;
import com.sam.alura.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, Object> {

    private final UserRepository userRepository;


    public UniqueUserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }



    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        NewUserRequest request = (NewUserRequest) value;
        return !userRepository.existsByUsernameOrEmail(request.username(), request.email());

    }
}
