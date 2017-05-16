package com.kostenko.webmydictionary.rest.users;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import com.kostenko.webmydictionary.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@RestController
@RequestMapping(Constants.Api.Rest.USERS)
public class UserRest {
    private final UserService userService;

    @Autowired
    public UserRest(UserService userService) {
        checkNotNull(userService, "UserService can't be null");
        this.userService = userService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        log.debug("in get all users");
        return userService.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "id") String login) {
        log.debug("in get concrete user. id=" + login);
        return userService.findById(login);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") String id, @RequestBody User newUser) {
        log.debug("in put. id=" + id);
        User another = userService.findByEmail(newUser.getEmail());
        if (another != null && !another.getId().equals(id)
                && !another.getEmail().equals(newUser.getEmail())) {
            log.debug("in put. user with this email is exists" + another.toString() + " vs " + newUser.toString());
            newUser.setEmail(null);
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
        userService.update(newUser);
        log.debug("in put. user updated...");
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "id") String id) {
        userService.remove(userService.findById(id));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        log.debug("in post");
        if (userService.findByLogin(newUser.getLogin()) != null) {
            newUser.setLogin(null);
            log.debug("in post. Login exists");
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
        if (userService.findByEmail(newUser.getEmail()) != null) {
            newUser.setEmail(null);
            log.debug("in post. Email exists");
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
        userService.create(newUser);
        log.debug("in post. All is good...F");
        return new ResponseEntity<>(userService.findByEmail(newUser.getEmail()), HttpStatus.OK);
    }
}
