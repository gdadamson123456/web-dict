package com.kostenko.webmydictionary.controllers.users;

import com.kostenko.webmydictionary.controllers.users.utils.EditForm;
import com.kostenko.webmydictionary.controllers.users.utils.ExistingUserValidator;
import com.kostenko.webmydictionary.controllers.users.utils.recaptcha.ReCaptchaChecker;
import com.kostenko.webmydictionary.controllers.users.utils.recaptcha.ReCaptchaCheckerResponse;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Controller
public class RegistrationController extends AbstractController implements Serializable {
    private static final long serialVersionUID = -1902249837028294777L;
    private static final Logger LOG = LoggerFactory.getLogger(EditController.class);
    private static final String MODE_REG = "reg";
    private static final String SECRET_KEY = "6LcskR8TAAAAAOPEUfFbTrUMbEAAmLPPCUfYX5xF";

    @Autowired
    private ExistingUserValidator userValidator;

    @RequestMapping(value = "/reg", method = RequestMethod.GET)
    public String openRegistrationForm(Model model) {
        model.addAttribute(Constants.MODEL_EDIT_FORM, new EditForm());
        model.addAttribute(Constants.MODE, MODE_REG);
        return Constants.View.EDIT_USER;
    }

    @RequestMapping(value = "/congratulation", method = RequestMethod.GET)
    public String openCongratulationView(Model model) {
        return "AfterRegisterView";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String registerUser(@ModelAttribute(Constants.MODEL_EDIT_FORM) @Valid EditForm editForm,
                               BindingResult bindingResult,
                               @RequestParam("g-recaptcha-response") String gRecaptchaResponse, Model model) {
        ReCaptchaCheckerResponse response = ReCaptchaChecker.checkReCaptcha(SECRET_KEY, gRecaptchaResponse);
        userValidator.validate(editForm, bindingResult);
        if (bindingResult.hasErrors() || !response.getSuccess()) {
            LOG.warn("input data is incorrect");
            model.addAttribute(Constants.MODE, MODE_REG);
            return Constants.View.EDIT_USER;
        }
        User user = getUser(editForm);
        userService.create(user);
        return "redirect:/congratulation";
    }

    @Override
    protected User getUser(EditForm editForm) {
        User user = editForm.getUserWithoutRole();
        user.setRole(roleService.findByName("user"));
        return user;
    }
}
