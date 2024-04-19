package com.sam.alura.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
class CreateUserController {

    private final RoleRepository roleRepository;

    public CreateUserController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping
    @Transactional
    ResponseEntity<?> createUser(@RequestBody @Valid NewUserRequest request) {
        User user = request.toModel(roleRepository);
        entityManager.persist(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
