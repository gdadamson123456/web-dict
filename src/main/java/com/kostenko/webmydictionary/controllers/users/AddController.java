package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.controllers.users.utils.EditForm;
import com.kostenko.webmydictionary.controllers.users.utils.ExistingUserValidator;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.Arrays;

import static com.google.common.base.Preconditions.checkNotNull;

@Controller
@Scope("session")
public class AddController extends AbstractController implements Serializable {
    private static final long serialVersionUID = -8212469842132320496L;
    private static final Logger LOG = LoggerFactory.getLogger(EditController.class);
    private static final String ADMIN_ADD = "/admin/add";
    private static final String MODE_ADD = "add";
    private final ExistingUserValidator userValidator;

    @Autowired
    public AddController(RoleService roleService, UserService userService, Utils utils, ExistingUserValidator userValidator) {
        super(roleService, userService, utils);
        checkNotNull(userValidator, "UserValidator can't be null");
        this.userValidator = userValidator;
    }

    @RequestMapping(value = ADMIN_ADD, method = RequestMethod.GET)
    public String showAddUserView(Model model) {
        setRolesToModel(model, Arrays.asList("user", "admin"));
        model.addAttribute(Constants.MODEL_EDIT_FORM, new EditForm());
        model.addAttribute(Constants.MODE, MODE_ADD);
        return Constants.View.EDIT_USER;
    }

    @RequestMapping(value = ADMIN_ADD, method = RequestMethod.POST)
    public String addUser(@ModelAttribute(Constants.MODEL_EDIT_FORM) @Valid EditForm editForm,
                          BindingResult bindingResult, Model model) {
        userValidator.validate(editForm, bindingResult);
        if (bindingResult.hasErrors()) {
            LOG.warn("input data is incorrect");
            setRolesToModel(model, Arrays.asList("user", "admin"));
            model.addAttribute(Constants.MODE, MODE_ADD);
            return Constants.View.EDIT_USER;
        }
        User user = getUser(editForm);
        userService.create(user);
        return Constants.View.RedirectionTo.ADMIN;
    }
}
