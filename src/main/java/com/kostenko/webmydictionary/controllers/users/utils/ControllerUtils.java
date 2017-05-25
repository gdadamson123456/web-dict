package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.utils.Constants;
import org.springframework.ui.Model;

public class ControllerUtils {

    public static String getUserHomeLink(User user, Model model) {
        if (user == null) {
            return Constants.Controller.RedirectionTo.LOGIN;
        }
        model.addAttribute("sessionUser", user);
        if (Constants.ROLE_ADMIN.equals(user.getRole().getName())) {
            return Constants.Controller.RedirectionTo.ADMIN;
        } else {
            return Constants.Controller.RedirectionTo.USER;
        }
    }
}
