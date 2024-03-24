package com.sam.alura.notification;

import com.sam.alura.course.Course;
import com.sam.alura.course.CourseRepository;
import com.sam.alura.course.NewFeedBackRequest;
import com.sam.alura.exceptions.AppException;
import com.sam.alura.helper.SystemHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotification implements NotificationService {
    private final CourseRepository courseRepository;

    public EmailNotification(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void send(NewFeedBackRequest request) {
        Course course = courseRepository.findById(request.courseId()).orElseThrow(() -> new AppException("course not found"));
        String ratingUser = SystemHelper.getUsername();
        String instructorEmail = course.getInstructor().getEmail();
        String subject = "Your course " + course.getName() + " got a new review";
        String body = ratingUser + " rated your course for "  + request.rating() + " and the reason is " + request.reason();
        System.out.println("------------------------------------------------");
        System.out.println("Simulating sending email to " + instructorEmail);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("-------------------------------------------------");
    }

}

