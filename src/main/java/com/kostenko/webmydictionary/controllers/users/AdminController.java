package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@Scope("session")
public class AdminController implements Serializable {
    private static final long serialVersionUID = 3948576066242283355L;
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        checkNotNull(userService, "UserService can't be null");
        this.userService = userService;
    }

    @RequestMapping(Constants.Controller.Path.ADMIN)
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return Constants.View.ADMIN;
    }
}
