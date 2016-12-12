package com.kostenko.webmydictionary.controllers;

import com.kostenko.webmydictionary.dao.domain.users.User;
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

    @Autowired
    public WelcomeController(Utils utils) {
        checkNotNull(utils, "Utils can't be null");
        this.utils = utils;
    }

    @RequestMapping(Constants.Controller.Path.APP_ROOT)
    public String index(Model model) {
        User user = utils.getUser();
        if (user != null) {
            return getUserHomeLink(user, model);
        }
        return Constants.Controller.RedirectionTo.LOGIN;
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
