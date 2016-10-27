package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.controllers.users.utils.EditForm;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
public abstract class AbstractController {
    protected final UserService userService;
    protected final RoleService roleService;
    protected final Utils utils;

    @Autowired
    public AbstractController(RoleService roleService, UserService userService, Utils utils) {
        checkNotNull(roleService, "RoleService can't be null");
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(utils, "Utils can't be null");
        this.roleService = roleService;
        this.utils = utils;
        this.userService = userService;
    }

    protected void setRolesToModel(Model model, List<String> roles) {
        model.addAttribute("roles", roles);
    }

    protected User getUser(EditForm editForm) {
        User user = editForm.getUserWithoutRole();
        String role = editForm.getRole();
        user.setRole(roleService.findByName(role));
        return user;
    }
}
