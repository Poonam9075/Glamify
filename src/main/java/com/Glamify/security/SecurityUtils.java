package com.Glamify.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static Long getLoggedInUserId() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("User not authenticated");
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return Long.valueOf(principal.getUserId());
    }
}
