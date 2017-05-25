package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    public CustomAuthenticationProvider(UserService userService, UserDetailsService userDetailsService) {
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(userDetailsService, "UserDetailsService can't be null");
        this.userService = userService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();
        log.debug("Username: %s", username);
        log.debug("Password: %s", password);
        if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
            User user = userService.findByLogin(username);
            if (user == null) {
                log.error("User is null");
                throw new BadCredentialsException("Username not found.");
            }
            if (!password.equals(user.getPassword())) {
                log.error("Passwords is not equal");
                throw new BadCredentialsException("Wrong password.");
            }
            UserDetails userDetail = userDetailsService.loadUserByUsername(user.getLogin());
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
            return new UsernamePasswordAuthenticationToken(userDetail, userDetail.getPassword(), authorities);
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
