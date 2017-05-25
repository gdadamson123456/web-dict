package com.kostenko.webmydictionary.utils;

import com.kostenko.webmydictionary.controllers.users.utils.CustomUserDetail;
import com.kostenko.webmydictionary.dao.domain.users.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Slf4j
@Service
public class SecurityContextUtils implements Serializable {
    private static final long serialVersionUID = 4181604947630989917L;

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            CustomUserDetail userDetails = (CustomUserDetail) auth.getPrincipal();
            user = userDetails.getUser();
        }
        return user;
    }
}
