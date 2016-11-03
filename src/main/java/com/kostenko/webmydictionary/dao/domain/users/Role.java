package com.kostenko.webmydictionary.dao.domain.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "roles")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 598302704073109831L;

    @Id
    private String id;
    private String name;

    public Role() {
        super();
    }

    public Role(String name) {
        super();
        this.name = name;
    }
}
