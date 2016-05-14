package com.kostenko.webmydictionary.services.implementation.users;

import com.kostenko.webmydictionary.dao.RoleDao;
import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service("roleDao")
public class RoleServiceImpl implements RoleService, Serializable {
    private static final long serialVersionUID = -7875165363111145688L;

    @Autowired
    private RoleDao roleDao;

    @Override
    public void create(Role role) {
        roleDao.create(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void remove(Role role) {
        roleDao.remove(role);
    }

    @Override
    public Role findByName(String name) {
        return roleDao.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }
}
