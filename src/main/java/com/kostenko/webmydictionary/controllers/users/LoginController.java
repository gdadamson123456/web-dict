package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.utils.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@Controller
public class LoginController implements Serializable {
    private static final long serialVersionUID = -7622903889055402474L;

    @RequestMapping(value = Constants.Controller.Path.LOGIN, method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout, Model model) {
        if (error != null) {
            model.addAttribute("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }
        return Constants.View.LOGIN;
    }
}
