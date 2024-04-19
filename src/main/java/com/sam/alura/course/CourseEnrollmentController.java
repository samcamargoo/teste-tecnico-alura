package com.sam.alura.course;

import com.sam.alura.shared.EnrollCourseValidator;
import com.sam.alura.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
class CourseEnrollmentController {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public CourseEnrollmentController(
            EnrollmentRepository enrollmentRepository,
            CourseRepository courseRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    @InitBinder
    void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new EnrollCourseValidator(entityManager, enrollmentRepository, courseRepository));
    }

    @PostMapping("/enroll")
    @Secured({"ROLE_ADMIN", "ROLE_STUDENT", "ROLE_INSTRUCTOR"})
    @Transactional
    ResponseEntity<?> joinCourse(@RequestBody @Valid EnrollCourseRequest request) {

        Enrollment enrollment = request.toModel(
                courseId -> entityManager.find(Course.class, courseId),
                userId -> entityManager.find(User.class, userId));

        enrollmentRepository.save(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
