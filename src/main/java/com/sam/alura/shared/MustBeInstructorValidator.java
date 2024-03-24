package com.sam.alura.shared;

import com.sam.alura.exceptions.AppException;
import com.sam.alura.user.ApplicationUser;
import com.sam.alura.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MustBeInstructorValidator implements ConstraintValidator<MustBeInstructor, Object> {

    private final UserRepository userRepository;

    public MustBeInstructorValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(value == null) {
            return true;
        }
        Long instructorId = (Long) value;
        ApplicationUser userEntity = userRepository.findById(instructorId).orElseThrow(() -> new AppException("instructor not found"));

        return userEntity.isInstructor();
    }
}
