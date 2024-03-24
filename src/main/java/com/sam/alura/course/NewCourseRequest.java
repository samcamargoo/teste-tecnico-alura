package com.sam.alura.course;

import com.sam.alura.shared.MustBeInstructor;
import com.sam.alura.shared.Unique;
import com.sam.alura.user.ApplicationUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.Function;

public record NewCourseRequest(@NotBlank(message = "is required") String name,
                               @Unique(fieldName = "code", domainClass = Course.class)
                               @Pattern(regexp = "^[a-zA-Z]+(?:-[a-zA-Z]+)*$", message = "is invalid")
                               @Length(max = 10, message = "must be equal or less than 10")
                               String code,
                               @NotNull(message = "is required")
                               @MustBeInstructor

                               Long instructorId,
                               @NotBlank(message = "is required")
                               String description
) {

    public Course toModel(Function<Long, ApplicationUser> loadUser) {
        @NotNull
        ApplicationUser instructor = loadUser.apply(instructorId);
        Assert.state(Objects.nonNull(instructor), "Instructor not found");
        return new Course(name, code, instructor, description);
    }
}
