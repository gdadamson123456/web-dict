package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static com.google.common.base.Preconditions.checkNotNull;

@Service
public class ExistingUserValidator implements Validator {
    private final UserService userService;

    @Autowired
    public ExistingUserValidator(UserService userService) {
        checkNotNull(userService, "UserService can't be null");
        this.userService = userService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return EditForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        EditForm user = (EditForm) target;
        if (userService.findByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "email", "Email is Exists");
        }
        if (userService.findByLogin(user.getLogin()) != null) {
            errors.rejectValue("login", "login", "Login is Exists");
        }
    }

}
