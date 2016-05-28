package com.kostenko.webmydictionary.dao.domain.users;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "users")
public class User implements Serializable {
    private static final long serialVersionUID = 788105459979932616L;
    private String id;
    private String login;
    private String password;
    private String email;
    private Role role;

    public User() {
        super();
    }

    public User(final String login, final String password, final String email, final Role role) {
        super();
        this.login = login;
        this.password = password;
        this.email = email;
        if (role != null) {
            this.role = role;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
