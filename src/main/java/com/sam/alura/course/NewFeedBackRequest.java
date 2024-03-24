package com.sam.alura.course;

import com.sam.alura.exceptions.AppException;
import com.sam.alura.helper.SystemHelper;
import com.sam.alura.shared.ExistsById;
import com.sam.alura.user.ApplicationUser;
import com.sam.alura.user.UserRepository;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NewFeedBackRequest(@ExistsById(domainClass = Course.class, fieldName = "id")
                                 @NotNull(message = "is required")
                                 Long courseId,
                                 @NotNull(message = "is required")
                                 @Min(value = 0, message = "must be equal or greater than 0")
                                 @Max(value = 10, message = "must be equal or less than 10")
                                 int rating,
                                 @NotBlank(message = "is required")
                                 String reason) {

    public CourseRating toModel(UserRepository userRepository, CourseRepository courseRepository) {
        String username = SystemHelper.getUsername();
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new AppException("Course not found"));
        ApplicationUser user = userRepository.findUserByUsername(username).orElseThrow(() -> new AppException("user not found"));
        return new CourseRating(course, user, reason, rating);
    }
}
