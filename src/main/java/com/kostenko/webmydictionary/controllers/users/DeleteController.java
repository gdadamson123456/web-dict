package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@Scope("session")
public class DeleteController implements Serializable {
    private static final long serialVersionUID = 6057559074566946472L;
    private static final String ADMIN_DELETE = Constants.View.Path.ADMIN + "/delete";
    private final UserService userService;

    @Autowired
    public DeleteController(UserService userService) {
        checkNotNull(userService, "UserService can't be null");
        this.userService = userService;
    }

    @RequestMapping(value = ADMIN_DELETE, method = RequestMethod.GET)
    public String deleteUser(@RequestParam("id") String id, Model model) {
        User current = new Utils().getUser();
        User user = userService.findById(id);
        if (current != null && !current.equals(user)) {
            if (user != null) {
                userService.remove(user);
            }
            return Constants.View.RedirectionTo.ADMIN;
        } else {
            model.addAttribute(Constants.ERROR_MESSAGE, "Can't delete current user");
            return Constants.View.RedirectionTo.ERROR;
        }
    }
}
