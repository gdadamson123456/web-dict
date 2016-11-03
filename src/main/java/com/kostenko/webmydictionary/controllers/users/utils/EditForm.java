package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import lombok.Data;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Service
@Data
public class EditForm {
    @NotNull
    @Size(min = 2, message = "minimum login size is 2 symbols")
    @Pattern(regexp = "^([a-zA-Z]+)$", message = "Login must consist only symbols")
    private String login;
    @NotNull
    @Size(min = 4, message = "Min password size = 4")
    @Pattern(regexp = "^([a-zA-Z])*([0-9])*$", message = "password must consist symbols and digits")
    private String password;
    @NotNull
    @Size(min = 4, message = "Min password size = 4")
    @Pattern(regexp = "^([a-zA-Z])*([0-9])*$", message = "password must consist symbols and digits")
    private String passwordRepeat;
    @NotNull
    @Size(min = 3, message = "minimum email size is 3 symbols")
    @Pattern(regexp = "^(\\w+\\.?)+@(\\w+\\.?)+$", message = "Email must be like this pattern [someAddress]@[someDomain]")
    private String email;
    private String role;
    private String id;
    @AssertTrue(message = "password verify field should be equal than pass field")
    private Boolean isSamePasswords;

    @AssertTrue(message = "This login is already registered")
    private boolean isValid() {
        isSamePasswords = this.password.equals(this.passwordRepeat);
        return isSamePasswords;
    }

    public User getUserWithoutRole() {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        user.setId(id == null || id.isEmpty() ? null : id);
        return user;
    }

    public void setUserData(User user) {
        login = user.getLogin();
        password = user.getPassword();
        passwordRepeat = user.getPassword();
        email = user.getEmail();
        id = user.getId();
        role = user.getRole().getName();
    }
}
