package com.kostenko.webmydictionary.dao.implementation.users;

import com.kostenko.webmydictionary.dao.UserDao;
import com.kostenko.webmydictionary.dao.domain.users.User;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Repository
public class ObjectifyUserDao implements UserDao, Serializable {
    private static final long serialVersionUID = -3225032021932775475L;


    @Override
    public void create(final User user) {

    }

    @Override
    public void update(final User user) {

    }

    @Override
    public void remove(final User user) {

    }

    @Override
    public List<User> findAll() {
        return Collections.emptyList();
    }

    @Override
    public User findByLogin(final String login) {
        return null;
    }

    @Override
    public User findById(final Long id) {
        return null;
    }

    @Override
    public User findByEmail(final String email) {
        return null;
    }

}
