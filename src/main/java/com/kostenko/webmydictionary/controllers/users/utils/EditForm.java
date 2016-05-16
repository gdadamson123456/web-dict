package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Service
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

    public Boolean getIsSamePasswords() {
        return isSamePasswords;
    }

    public void setIsSamePasswords(Boolean isSamePasswords) {
        this.isSamePasswords = isSamePasswords;
    }

    @AssertTrue(message = "This login is already registered")
    private boolean isValid() {
        isSamePasswords = this.password.equals(this.passwordRepeat);
        return isSamePasswords;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
