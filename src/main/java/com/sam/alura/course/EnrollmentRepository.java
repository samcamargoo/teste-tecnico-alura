package com.sam.alura.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    boolean existsByUserIdAndCourseId(Long userId, Long courseId);
    Optional<Enrollment> findByCourseId(Long courseId);


}
