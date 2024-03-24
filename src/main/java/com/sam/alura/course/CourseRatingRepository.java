package com.sam.alura.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRatingRepository extends JpaRepository<CourseRating, Long> {


    List<CourseRating> findAllByCourseId(Long id);
}
