package com.sam.alura.shared;

import com.sam.alura.course.CourseRepository;
import com.sam.alura.course.CourseStatus;
import com.sam.alura.course.EnrollCourseRequest;
import com.sam.alura.course.EnrollmentRepository;
import com.sam.alura.exceptions.AppException;
import com.sam.alura.helper.SystemHelper;
import com.sam.alura.user.User;
import jakarta.persistence.EntityManager;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;


public class EnrollCourseValidator implements Validator {

    private final EnrollmentRepository enrollmentRepository;

    private final CourseRepository courseRepository;
    private final EntityManager entityManager;

    public EnrollCourseValidator(EntityManager entityManager, EnrollmentRepository enrollmentRepository, CourseRepository courseRepository) {
        this.entityManager = entityManager;
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EnrollCourseRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) {
            return;
        }
        EnrollCourseRequest enrollCourseRequest = (EnrollCourseRequest) target;

        Long userId = SystemHelper.getUserID();
        User user = entityManager.find(User.class, userId);
        Assert.state(Objects.nonNull(user), "user not found");

        boolean alreadyJoined = enrollmentRepository.existsByUserIdAndCourseId(user.getId(), enrollCourseRequest.courseId());
        boolean courseIsInactive = courseRepository.existsByIdAndStatus(enrollCourseRequest.courseId(), CourseStatus.INACTIVE);

        if (courseIsInactive) {
            throw new AppException("This course is inactive, you can't join.");
        }
        if (alreadyJoined) {
            throw new AppException("You are already enrolled in this course.");
        }
    }
}
