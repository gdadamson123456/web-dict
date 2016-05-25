package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetailImpl extends org.springframework.security.core.userdetails.User {
    private static final long serialVersionUID = -5487662132658679522L;
    private User user;

    public UserDetailImpl(String username, String password, boolean enabled, boolean accountNonExpired,
                          boolean credentialsNonExpired, boolean accountNonLocked,
                          Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        UserDetailImpl that = (UserDetailImpl) o;

        return user != null ? user.equals(that.user) : that.user == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (user != null ? user.hashCode() : 0);
        return result;
    }
}
