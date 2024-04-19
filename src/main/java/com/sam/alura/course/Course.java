package com.sam.alura.course;

import com.sam.alura.user.User;
import jakarta.persistence.*;
import org.springframework.util.Assert;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, length = 10)
    private String code;
    @ManyToOne(fetch = FetchType.LAZY)
    private User instructor;
    private String description;

    @OneToMany(mappedBy = "course")
    private Set<Enrollment> enrollments = new HashSet<>();
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime inactivatedAt;

    @Deprecated
    public Course() {
    }

    public Course(String name, String code, User user, String description) {
        Assert.state(user.isInstructor(), user.getName() + " is not an instructor");

        Assert.hasLength(name, "course name is required");
        Assert.state(Objects.nonNull(name), "course name cannot be null");

        Assert.hasLength(code, "course code is required");
        Assert.state(Objects.nonNull(code), "course code cannot be null");

        Assert.hasLength(description, "course code is required");
        Assert.state(Objects.nonNull(description), "course code cannot be null");

        this.name = name;
        this.code = code;
        this.instructor = user;
        this.description = description;
        this.status = CourseStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public User getInstructor() {
        return instructor;
    }

    public String getDescription() {
        return description;
    }

    public CourseStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getInactivatedAt() {
        return inactivatedAt;
    }


    public void inactivate() {
        this.status = CourseStatus.INACTIVE;
        this.inactivatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(code, course.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }
}
