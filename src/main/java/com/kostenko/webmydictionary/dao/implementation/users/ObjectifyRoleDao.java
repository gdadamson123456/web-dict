package com.kostenko.webmydictionary.dao.implementation.users;

import com.kostenko.webmydictionary.dao.RoleDao;
import com.kostenko.webmydictionary.dao.domain.users.Role;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Repository
public class ObjectifyRoleDao implements RoleDao, Serializable {
    private static final long serialVersionUID = 3893806746257241542L;

    @Override
    public void create(final Role role) {

    }

    @Override
    public void update(final Role role) {

    }

    @Override
    public void remove(final Role role) {


    }

    @Override
    public Role findByName(String name) {

        return null;
    }

    @Override
    public List<Role> findAll() {
        return Collections.emptyList();
    }

}
