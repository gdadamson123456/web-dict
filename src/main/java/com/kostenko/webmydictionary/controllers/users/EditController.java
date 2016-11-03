package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.controllers.users.utils.EditForm;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Controller
@Scope("session")
public class EditController extends AbstractController implements Serializable {
    private static final long serialVersionUID = 3870664333932998458L;
    private static final String ADMIN_EDIT = "/admin/edit";
    private static final String MODE_EDIT = "edit";

    @Autowired
    public EditController(RoleService roleService, UserService userService, Utils utils) {
        super(roleService, userService, utils);
    }

    @RequestMapping(value = ADMIN_EDIT, method = RequestMethod.GET)
    public String showEditUserView(@RequestParam("login") String login, Model model) {
        EditForm form = new EditForm();
        form.setUserData(userService.findByLogin(login));
        setRolesToModel(model, getCorrectRolesList(utils.getUser()));
        model.addAttribute(Constants.MODEL_EDIT_FORM, form);
        model.addAttribute(Constants.MODE, MODE_EDIT);
        return Constants.View.EDIT_USER;
    }

    @RequestMapping(value = ADMIN_EDIT, method = RequestMethod.POST)
    public String editUser(@ModelAttribute(Constants.MODEL_EDIT_FORM) @Valid EditForm editForm,
                           BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.warn("input data is incorrect");
            setRolesToModel(model, getCorrectRolesList(utils.getUser()));
            model.addAttribute(Constants.MODE, MODE_EDIT);
            return Constants.View.EDIT_USER;
        }
        User user = getUser(editForm);
        userService.update(user);
        return Constants.Controller.RedirectionTo.ADMIN;
    }

    private List<String> getCorrectRolesList(User user) {
        List<String> roles;
        if (Constants.ROLE_ADMIN.equals(user.getRole().getName())) {
            roles = Arrays.asList("admin", "user");
        } else {
            roles = Arrays.asList("user", "admin");
        }
        return roles;
    }
}
