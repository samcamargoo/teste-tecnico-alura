package com.sam.alura.admin;

import com.sam.alura.course.*;
import com.sam.alura.exceptions.AppException;
import com.sam.alura.user.ApplicationUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/courses")
public class CourseController {


    private final NPSService npsService;
    private final CourseRepository courseRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public CourseController(CourseRepository courseRepository, NPSService npsService) {
        this.courseRepository = courseRepository;
        this.npsService = npsService;
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    @Transactional
    ResponseEntity<?> createCourse(@RequestBody @Valid NewCourseRequest request) {
        Course course = request.toModel(id -> entityManager.find(ApplicationUser.class, id));
        entityManager.persist(course);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/disable/{courseId}")
    @Secured("ROLE_ADMIN")
    @Transactional
    ResponseEntity<?> disableCourse(@PathVariable("courseId") Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new AppException("course not found"));
        course.inactivate();
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Secured("ROLE_ADMIN")
    ResponseEntity<?> listCourses(@RequestParam(name = "page", defaultValue = "0") int page,
                                  @RequestParam(name = "size", defaultValue = "10") int size,
                                  @RequestParam(name = "status", required = false) String status) {

        Page<CourseResponse> courses;
        Pageable pageable = PageRequest.of(page, size);

        if (status != null && !status.isEmpty()) {
            try {
                courses = courseRepository
                        .findByStatus(pageable, CourseStatus.valueOf(status.toUpperCase()))
                        .map(CourseResponse::new);
                return ResponseEntity.ok(courses);
            } catch (IllegalArgumentException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Only active and inactive are accepted as status");
            }
        }
        courses = courseRepository.findAll(pageable).map(CourseResponse::new);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/report")
    @Secured("ROLE_ADMIN")
    ResponseEntity<?> listAllCoursesReport(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursesWithMoreThanFourEnrollments = courseRepository.findCoursesWithMoreThanFourEnrollments(pageable);
        Page<NPSResponse> nps = coursesWithMoreThanFourEnrollments.map(npsService::calculateNPS);
        return ResponseEntity.ok(nps);
    }

    @GetMapping("/report/{courseId}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<?> listReportByCourseId(@PathVariable("courseId") Long courseId) {

        Course course = courseRepository.findByIdAndEnrollmentsGreaterThan(courseId, 4).orElseThrow(() ->
                new AppException("Course not found or doesn't meet the criteria of minimum 5 enrollments to calculate NPS"));

        NPSResponse npsResponse = npsService.calculateNPS(course);
        return ResponseEntity.ok(npsResponse);
    }
}
