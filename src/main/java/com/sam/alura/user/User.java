package com.sam.alura.user;


import com.sam.alura.course.Enrollment;
import com.sam.alura.exceptions.AppException;
import jakarta.persistence.*;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "tb_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true, length = 20)
    private String username;
    @Column(unique = true)
    private String email;
    private String password;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user")
    private Set<Enrollment> enrollments = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tb_user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @Deprecated
    public User() {
    }

    public User(String name, String username, String email, CleanPassword cleanPassword, Role role) {
        Assert.hasLength(name, "name cannot be empty");
        Assert.state(Objects.nonNull(name), "name cannot be null");

        Assert.hasLength(username, "username cannot be empty");
        Assert.state(Objects.nonNull(username), "username cannot be null");

        Assert.hasLength(email, "email cannot be empty");
        Assert.state(Objects.nonNull(email), "email cannot be null");

        Assert.state(Objects.nonNull(cleanPassword), "cleanpassword cannot be null");

        Assert.state(Objects.nonNull(role), "role cannot be null");



        this.name = name;
        this.username = username;
        this.email = email;
        this.password = cleanPassword.encode();
        this.createdAt = LocalDateTime.now();
        this.roles.add(role);
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public String getRole() {
        return this.roles.stream().findFirst().orElseThrow(() -> new AppException("role not found")).getRoleType();
    }

    public boolean isInstructor() {
        return this.roles.stream().anyMatch(x -> x.getRoleType().equals("INSTRUCTOR"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User userEntity = (User) o;
        return Objects.equals(username, userEntity.username) && Objects.equals(email, userEntity.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email);
    }
}
