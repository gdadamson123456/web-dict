package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.controllers.users.utils.EditForm;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.SecurityContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Controller
public abstract class AbstractController {
    protected final UserService userService;
    protected final RoleService roleService;
    protected final SecurityContextUtils securityContextUtils;

    @Autowired
    public AbstractController(RoleService roleService, UserService userService, SecurityContextUtils securityContextUtils) {
        checkNotNull(roleService, "RoleService can't be null");
        checkNotNull(userService, "UserService can't be null");
        checkNotNull(securityContextUtils, "Utils can't be null");
        this.roleService = roleService;
        this.securityContextUtils = securityContextUtils;
        this.userService = userService;
    }

    protected User getUser(EditForm editForm) {
        User user = editForm.getUserWithoutRole();
        String role = editForm.getRole();
        user.setRole(roleService.findByName(role));
        return user;
    }
}
