package com.sam.alura.security;

import com.sam.alura.user.ApplicationUser;
import com.sam.alura.user.CustomUserDetails;
import com.sam.alura.user.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user not found"));
        return new CustomUserDetails(user);
    }
}
