package com.kostenko.webmydictionary.dao;

import com.kostenko.webmydictionary.dao.domain.users.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
    Role findByName(String name);
    Role findById(String id);
}
