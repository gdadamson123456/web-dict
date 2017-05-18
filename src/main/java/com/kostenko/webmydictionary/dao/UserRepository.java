package com.kostenko.webmydictionary.dao;

import com.kostenko.webmydictionary.dao.domain.users.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);

    User findByEmail(String email);

    User findById(String id);

    User findBySessionId(String sessionId);
}
