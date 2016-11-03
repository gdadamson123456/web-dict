package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.configuration.AppConfigLoader;
import com.kostenko.webmydictionary.configuration.AppConfigLoader.Property;
import com.kostenko.webmydictionary.controllers.users.utils.EditForm;
import com.kostenko.webmydictionary.controllers.users.utils.ExistingUserValidator;
import com.kostenko.webmydictionary.controllers.users.utils.recaptcha.ReCaptchaChecker;
import com.kostenko.webmydictionary.controllers.users.utils.recaptcha.ReCaptchaCheckerResponse;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.RoleService;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import com.kostenko.webmydictionary.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Controller
public class RegistrationController extends AbstractController implements Serializable {
    private static final long serialVersionUID = -1902249837028294777L;
    private static final String MODE_REG = "reg";
    private final String secretKey;
    private final ExistingUserValidator userValidator;
    private final String verifyUrl;

    @Autowired
    public RegistrationController(RoleService roleService, UserService userService, Utils utils, ExistingUserValidator userValidator, AppConfigLoader appConfigLoader) {
        super(roleService, userService, utils);
        checkNotNull(userValidator, "UserValidator can't be null");
        checkNotNull(appConfigLoader, "AppConfigLoader can't be null");
        this.userValidator = userValidator;
        this.secretKey = appConfigLoader.getProperty(Property.RECAPTCHA_KEY_PRIVATE);
        this.verifyUrl = appConfigLoader.getProperty(Property.RECAPTCHA_VERIFY_URL);
    }

    @RequestMapping(value = Constants.Controller.Path.REGISTRATION, method = RequestMethod.GET)
    public String openRegistrationForm(Model model) {
        model.addAttribute(Constants.MODEL_EDIT_FORM, new EditForm());
        model.addAttribute(Constants.MODE, MODE_REG);
        return Constants.View.EDIT_USER;
    }

    @RequestMapping(value = Constants.Controller.Path.CONGRATULATION, method = RequestMethod.GET)
    public String openCongratulationView(Model model) {
        return "AfterRegisterView";
    }

    @RequestMapping(value = Constants.Controller.Path.REGISTRATION, method = RequestMethod.POST)
    public String registerUser(@ModelAttribute(Constants.MODEL_EDIT_FORM) @Valid EditForm editForm,
                               BindingResult bindingResult,
                               @RequestParam("g-recaptcha-response") String gRecaptchaResponse, Model model) {
        ReCaptchaCheckerResponse response = ReCaptchaChecker.checkReCaptcha(secretKey, gRecaptchaResponse, verifyUrl);
        userValidator.validate(editForm, bindingResult);
        if (bindingResult.hasErrors() || !response.getSuccess()) {
            log.warn("input data is incorrect");
            model.addAttribute(Constants.MODE, MODE_REG);
            return Constants.View.EDIT_USER;
        }
        User user = getUser(editForm);
        userService.create(user);
        return Constants.Controller.RedirectionTo.CONGRATULATION;
    }

    @Override
    protected User getUser(EditForm editForm) {
        User user = editForm.getUserWithoutRole();
        user.setRole(roleService.findByName(Constants.ROLE_USER));
        return user;
    }
}
