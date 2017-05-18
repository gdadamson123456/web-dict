package com.kostenko.webmydictionary.services.implementation.users;

import com.kostenko.webmydictionary.dao.UserRepository;
import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service(value = "userService")
public class UserServiceImpl implements UserService, Serializable {
    private static final long serialVersionUID = 1746303374630617701L;
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        checkNotNull(userRepository, "UserRepository can't be null");
        this.userRepository = userRepository;
    }

    @Override
    public void create(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        userRepository.save(user);
    }

    @Override
    public void remove(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findById(String id) {
        return userRepository.findById(id);
    }

    @Override
    public User findBySessionId(String sessionId) {
        return userRepository.findBySessionId(sessionId);
    }
}
