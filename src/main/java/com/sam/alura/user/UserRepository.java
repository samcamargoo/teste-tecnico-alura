package com.sam.alura.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<ApplicationUser, Long> {

    Optional<ApplicationUser> findUserByUsername(String username);

}
