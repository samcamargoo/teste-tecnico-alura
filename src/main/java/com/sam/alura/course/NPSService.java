package com.sam.alura.course;

import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NPSService {

    private final CourseRatingRepository courseRatingRepository;

    public NPSService(CourseRatingRepository courseRatingRepository) {
        this.courseRatingRepository = courseRatingRepository;
    }

    public NPSResponse calculateNPS(Course course) {
        List<CourseRating> courseRating = courseRatingRepository.findAllByCourseId(course.getId());
        long answers = courseRating.size();
        long detractors = courseRating.stream().filter(currentCourse -> currentCourse.getRating() >= 0 && currentCourse.getRating() <= 6).count();
        long promoters = courseRating.stream().filter(currentCourse -> currentCourse.getRating() >= 9 && currentCourse.getRating() <= 10).count();

        double npsDouble = ((double)(promoters - detractors) / answers) * 100;
        long nps = Math.round(npsDouble);
        return new NPSResponse(course.getName(), nps, promoters, detractors);

    }
}
