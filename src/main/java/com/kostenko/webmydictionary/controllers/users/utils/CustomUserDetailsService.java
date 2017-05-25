package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service("UserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsService(UserService userService) {
        checkNotNull(userService, "UserService can't be null");
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        log.debug("Login: %s", login);
        com.kostenko.webmydictionary.dao.domain.users.User user = userService.findByLogin(login);
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getName()));
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.kostenko.webmydictionary.dao.domain.users.User user, List<GrantedAuthority> authorities) {
        CustomUserDetail detailUser = new CustomUserDetail(user.getLogin(), user.getPassword(), true, true, true, true, authorities);
        detailUser.setUser(user);
        return detailUser;
    }
}
