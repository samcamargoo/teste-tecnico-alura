package com.sam.alura.admin;

import com.sam.alura.exceptions.AppException;
import com.sam.alura.user.ApplicationUser;
import com.sam.alura.user.UserRepository;
import com.sam.alura.user.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/users")
class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/{username}")
    @Secured("ROLE_ADMIN")
    ResponseEntity<?> findUsername(@PathVariable("username") String username) {

        ApplicationUser user = userRepository.findUserByUsername(username).orElseThrow(() -> new AppException("username not found"));
        UserResponse userResponse = new UserResponse(user);
        return ResponseEntity.ok(userResponse);
    }
}
