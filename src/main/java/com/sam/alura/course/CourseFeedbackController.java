package com.sam.alura.course;

import com.sam.alura.notification.EmailNotification;
import com.sam.alura.shared.FeedbackCourseValidator;
import com.sam.alura.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses/feedback")
class CourseFeedbackController {

    private final EmailNotification emailNotification;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CourseFeedbackController(UserRepository userRepository,
                                    EnrollmentRepository enrollmentRepository,
                                    EmailNotification emailNotification,
                                    CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.emailNotification = emailNotification;
        this.courseRepository = courseRepository;
    }

    @InitBinder
    void init(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new FeedbackCourseValidator(enrollmentRepository, userRepository));
    }

    @PostMapping
    @Secured("ROLE_STUDENT")
    @Transactional
    ResponseEntity<?> registerFeedback(@RequestBody @Valid NewFeedBackRequest request) {

        if (request.rating() < 6) {
            emailNotification.send(request);
        }
        CourseRating courseRating = request.toModel(userRepository, courseRepository);
        entityManager.persist(courseRating);
        return ResponseEntity.ok().build();

    }

}
