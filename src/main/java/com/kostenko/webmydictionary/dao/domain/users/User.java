package com.kostenko.webmydictionary.dao.domain.users;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    public static final String FIELD_ID = "id";
    public static final String FIELD_LOGIN = "login";
    public static final String FIELD_PASSWORD = "password";
    public static final String FIELD_EMAIL = "email";
    public static final String FIELD_FIRST_NAME = "firstName";
    public static final String FIELD_LAST_NAME = "lastName";
    public static final String FIELD_BIRTHDAY = "birthday";
    public static final String FIELD_ROLE = "role";
    private static final long serialVersionUID = 788105459979932616L;
    private Long id;
    private String login;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    @XmlJavaTypeAdapter(SqlDateAdapter.class)
    private Date birthday;
    private Role role;

    public User() {
        super();
    }

    public User(final String login, final String password, final String email, final String firstName,
                final String lastName, final Date birthdayDate, final Role role) {
        super();
        this.login = login;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthdayDate;
        if (role != null) {
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return id + ":" + login + ":" + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (!login.equals(user.login)) return false;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        int result = login.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
