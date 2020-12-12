package com.derick.services;

import com.derick.security.UserDetailsImpl;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserService {

    public static UserDetailsImpl authenticatedUser() {
        try {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception e) {
            return null;
        }
    }
}
