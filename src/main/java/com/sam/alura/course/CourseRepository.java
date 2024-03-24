package com.sam.alura.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    Page<Course> findByStatus(Pageable pageable, CourseStatus status);
    boolean existsByIdAndStatus(Long id, CourseStatus status);

    @Query("SELECT c FROM Course c WHERE SIZE(c.enrollments) > 4")
    Page<Course> findCoursesWithMoreThanFourEnrollments(Pageable pageable);


    @Query("SELECT c FROM Course c WHERE SIZE(c.enrollments) > 4")
    Optional<Course> findByIdAndEnrollmentsGreaterThan(Long courseId, int enrollments);
}
