package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Controller
public class LoginController implements Serializable {
    private static final long serialVersionUID = -7622903889055402474L;
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        checkNotNull(userService, "userService can't be null");
        this.userService = userService;
    }

    @RequestMapping(value = Constants.Controller.Path.LOGIN, method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @PathVariable(value = "token", required = false) String token,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        if (token != null) {
            User user = userService.findBySessionId(token);
            if (user != null) {
                return getUserHomeLink(user, model);
            }
        }
        return Constants.View.LOGIN;
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
