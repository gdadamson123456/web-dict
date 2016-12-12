package com.kostenko.webmydictionary.dao.domain.users;

import com.kostenko.webmydictionary.utils.Constants;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Slf4j
@Data
@Document(collection = Constants.DataBase.TABLE_USERS)
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
        log.debug(String.format("Creating user with login:%s, password:%s, email:%s, role:%s", login, password, email, role));
    }
}
