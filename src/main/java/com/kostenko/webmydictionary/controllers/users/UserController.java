package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Controller
@Scope("session")
public class UserController implements Serializable {
    private static final long serialVersionUID = -7895793397104017186L;
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        checkNotNull(userService, "UserService can't be null");
        this.userService = userService;
    }

    @RequestMapping(value = Constants.Controller.Path.USER, method = RequestMethod.GET)
    public String login(Model model) {
        model.addAttribute("users", userService.findAll());
        return Constants.View.USER;
    }
}
