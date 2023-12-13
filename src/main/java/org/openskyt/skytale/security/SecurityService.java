package org.openskyt.skytale.security;

import lombok.AllArgsConstructor;
import org.openskyt.skytale.models.User;
import org.openskyt.skytale.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SecurityService {
    UserService userService;

    public User getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userService.getByUsername(authentication.getName());
    }

}
