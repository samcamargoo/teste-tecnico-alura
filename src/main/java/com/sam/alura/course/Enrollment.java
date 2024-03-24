package com.sam.alura.course;

import com.sam.alura.user.ApplicationUser;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tb_enrollments")
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private ApplicationUser user;
    @ManyToOne(fetch = FetchType.LAZY)
    private Course course;
    private LocalDateTime registrationDate;

    @Deprecated
    public Enrollment() {
    }

    public Enrollment(ApplicationUser user, Course course) {
        Assert.state(Objects.nonNull(user), "user cannot be null");
        Assert.state(Objects.nonNull(course), "course cannot be null");
        this.user = user;
        this.course = course;
        this.registrationDate = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Enrollment that = (Enrollment) o;
        return Objects.equals(user, that.user) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, course);
    }
}
