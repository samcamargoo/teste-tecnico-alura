package com.sam.alura.course;

import com.sam.alura.helper.SystemHelper;
import com.sam.alura.shared.ExistsById;
import com.sam.alura.user.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.function.Function;

public record EnrollCourseRequest(
        @NotNull(message = "is required")
        @ExistsById(domainClass = Course.class, fieldName = "id")
        Long courseId) {


    public Enrollment toModel(Function<Long, Course> loadCourse, Function<Long, User> loadUser) {

        Long userId = SystemHelper.getUserID();
        User user = loadUser.apply(userId);
        Course course = loadCourse.apply(courseId);

        Assert.state(Objects.nonNull(user), "User not found");
        Assert.state(Objects.nonNull(course), "course not found");
        return new Enrollment(user, course);

    }
}
