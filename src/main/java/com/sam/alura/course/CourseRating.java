package com.sam.alura.course;

import com.sam.alura.user.User;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name = "tb_courses_rating")
public class CourseRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    @ManyToOne(fetch = FetchType.LAZY)
    private User rater;

    private String reason;
    private int rating;

    private LocalDateTime ratedAt;

    @Deprecated
    public CourseRating() {
    }

    public CourseRating(Course course, User rater, String reason, int rating) {
        Assert.state(Objects.nonNull(course), "course can't be null");
        Assert.state(Objects.nonNull(rater), "rater can't be null");
        this.course = course;
        this.rater = rater;
        this.reason = reason;
        this.rating = rating;
        this.ratedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public User getRater() {
        return rater;
    }

    public String getReason() {
        return reason;
    }

    public int getRating() {
        return rating;
    }
}
