package com.sam.alura.shared;


import com.sam.alura.course.EnrollmentRepository;
import com.sam.alura.course.NewFeedBackRequest;
import com.sam.alura.exceptions.AppException;
import com.sam.alura.helper.SystemHelper;
import com.sam.alura.user.ApplicationUser;
import com.sam.alura.user.UserRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class FeedbackCourseValidator implements Validator {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;

    public FeedbackCourseValidator(EnrollmentRepository enrollmentRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewFeedBackRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        if (errors.hasErrors()) {
            return;
        }

        NewFeedBackRequest request = (NewFeedBackRequest) target;
        String username = SystemHelper.getUsername();
        ApplicationUser user = userRepository.findUserByUsername(username).orElseThrow(() -> new AppException("username not found"));
        boolean isEnroled = enrollmentRepository.existsByUserIdAndCourseId(user.getId(), request.courseId());


       if(!isEnroled) {
           throw new AppException("you cannot rate a course that you are not enrolled with");
       }

    }
}
