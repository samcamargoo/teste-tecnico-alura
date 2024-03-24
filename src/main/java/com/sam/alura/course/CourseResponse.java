package com.sam.alura.course;

import java.time.LocalDateTime;

public record CourseResponse(String name, String code,
                             String instructor,
                             String description,
                             CourseStatus status,
                             LocalDateTime createdAt,
                             LocalDateTime inactivatedAt) {

    public CourseResponse(Course course) {
        this(
                course.getName(),
                course.getCode(),
                course.getInstructor().getName(),
                course.getDescription(),
                course.getStatus(),
                course.getCreatedAt(),
                course.getInactivatedAt());
    }
}
