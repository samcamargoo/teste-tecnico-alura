package com.sam.alura.helper;

import com.sam.alura.user.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SystemHelper {

    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static Long getUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            CustomUserDetails customUser = (CustomUserDetails) authentication.getPrincipal();
            return customUser.getId();
        }
        return null;
    }
}

