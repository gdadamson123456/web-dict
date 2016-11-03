package com.kostenko.webmydictionary.controllers;

import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Controller
@SessionAttributes({"sessionUser"})
public class WelcomeController implements Serializable {
    private static final long serialVersionUID = 3954475661965470388L;
    private final Utils utils;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public WelcomeController(Utils utils, UserService userService, RoleService roleService) {
        checkNotNull(utils, "Utils can't be null");
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(roleService, "RoleService can't be null");
        this.utils = utils;
        this.userService = userService;
        this.roleService = roleService;
    }

    @RequestMapping(Constants.Controller.Path.APP_ROOT)
    public String index(Model model) {
        createData();
        User user = utils.getUser();
        if (user != null) {
            return getUserHomeLink(user, model);
        }
        return Constants.Controller.RedirectionTo.LOGIN;
    }

    private void createData() { //TODO: create db script to create user and delete this code
        if (roleService.findByName(Constants.ROLE_ADMIN) == null) {
            Role role = new Role(Constants.ROLE_ADMIN);
            role.setId(String.valueOf(1));
            roleService.create(role);
        }
        if (roleService.findByName(Constants.ROLE_USER) == null) {
            Role role = new Role(Constants.ROLE_USER);
            role.setId(String.valueOf(2));
            roleService.create(role);
        }
        if (userService.findByEmail("sanyokkua@gmail.com") == null) {
            User user = new User("admin",
                    "admin",
                    "sanyokkua@gmail.com",
                    roleService.findByName(Constants.ROLE_ADMIN));
            user.setId(String.valueOf(1));
            userService.create(user);
        }
    }

    private String getUserHomeLink(User user, Model model) {
        model.addAttribute("sessionUser", user);
        if (Constants.ROLE_ADMIN.equals(user.getRole().getName())) {
            return Constants.Controller.RedirectionTo.ADMIN;
        } else {
            return Constants.Controller.RedirectionTo.USER;
        }
    }
}
