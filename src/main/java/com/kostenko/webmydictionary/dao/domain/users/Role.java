package com.kostenko.webmydictionary.dao.domain.users;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "roles")
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        if (!id.equals(role.id)) return false;
        return name.equals(role.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}
