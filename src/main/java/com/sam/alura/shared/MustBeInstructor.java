package com.sam.alura.shared;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MustBeInstructorValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustBeInstructor {
    String message() default "must be an instructor";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
