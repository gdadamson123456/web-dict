package com.kostenko.webmydictionary.services;

import com.kostenko.webmydictionary.dao.domain.users.Role;

import java.util.List;

public interface RoleService {
    void create(Role role);

    void update(Role role);

    void remove(Role role);

    Role findByName(String name);

    Role findById(String id);

    List<Role> findAll();
}
