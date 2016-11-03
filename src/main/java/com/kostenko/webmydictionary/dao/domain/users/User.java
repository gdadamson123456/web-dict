package com.kostenko.webmydictionary.dao.domain.users;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "users")
@Data
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
}
