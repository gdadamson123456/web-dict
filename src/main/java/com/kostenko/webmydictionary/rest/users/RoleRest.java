package com.kostenko.webmydictionary.rest.users;

import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rest/roles")
public class RoleRest {
    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Role> getRoles() {
        return roleService.findAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public Role getRole(@PathVariable(value = "name") String name) {
        return roleService.findByName(name);
    }
}
