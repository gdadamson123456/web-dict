package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(final String login) throws UsernameNotFoundException {
        com.kostenko.webmydictionary.dao.domain.users.User user = userService.findByLogin(login);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(com.kostenko.webmydictionary.dao.domain.users.User user,
                                            List<GrantedAuthority> authorities) {
        UserDetailImpl detailUser = new UserDetailImpl(user.getLogin(), user.getPassword(), true, true, true, true, authorities);
        detailUser.setUser(user);
        return detailUser;
    }

    private List<GrantedAuthority> buildUserAuthority(Role userRoles) {
        Set<GrantedAuthority> setAuths = new HashSet<>();
        setAuths.add(new SimpleGrantedAuthority(userRoles.getName()));
        List<GrantedAuthority> Result = new ArrayList<>(setAuths);
        return Result;
    }
}
