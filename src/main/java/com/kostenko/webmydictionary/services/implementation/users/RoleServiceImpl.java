package com.kostenko.webmydictionary.services.implementation.users;

import com.kostenko.webmydictionary.dao.RoleRepository;
import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService, Serializable {
    private static final long serialVersionUID = -7875165363111145688L;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void create(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void update(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void remove(Role role) {
        roleRepository.delete(role);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
