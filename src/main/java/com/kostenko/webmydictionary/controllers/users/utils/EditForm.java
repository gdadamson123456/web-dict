package com.kostenko.webmydictionary.controllers.users.utils;

import com.kostenko.webmydictionary.dao.domain.users.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

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
    @NotNull
    @Size(min = 2, message = "minimum first name size is 2 symbols")
    @Pattern(regexp = "^([a-zA-Z]+)$", message = "First name must consist only symbols")
    private String firstName;
    @NotNull
    @Size(min = 2, message = "minimum last name size is 2 symbols")
    @Pattern(regexp = "^([a-zA-Z]+)$", message = "Last Name must consist only symbols")
    private String lastName;
    @NotNull
    @Pattern(regexp = "^[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$", message = "Incorrect date format. Please, check format for next pattern yyyy-mm-dd")
    private String birthday;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setBirthday(Date.valueOf(birthday));
        user.setPassword(password);
        user.setId(id == null || id.isEmpty() ? null : Long.valueOf(id));
        return user;
    }

    public void setUserData(User user) {
        login = user.getLogin();
        password = user.getPassword();
        passwordRepeat = user.getPassword();
        email = user.getEmail();
        firstName = user.getFirstName();
        lastName = user.getLastName();
        birthday = user.getBirthday().toString();
        long userId = user.getId();
        id = String.valueOf(userId);
        role = user.getRole().getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
