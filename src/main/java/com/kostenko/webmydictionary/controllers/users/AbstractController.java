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

@Controller
public abstract class AbstractController {
    @Autowired
    protected UserService userService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected Utils utils;
    protected List<String> roles;

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
