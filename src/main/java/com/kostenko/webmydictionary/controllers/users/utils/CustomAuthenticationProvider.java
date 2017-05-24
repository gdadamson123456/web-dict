package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkNotNull;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    CustomAuthenticationProvider(UserService userService, UserDetailsServiceImpl userDetailsService) {
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(userDetailsService, "UserDetailsService can't be null");
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            User user = userService.findByLogin(username);
            if (user == null) {
                throw new BadCredentialsException("Username not found.");
            }
            if (!password.equals(user.getPassword())) {
                throw new BadCredentialsException("Wrong password.");
            }
            UserDetails userDetail = userDetailsService.loadUserByUsername(user.getLogin());
            return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
