package com.kostenko.webmydictionary.rest.users;

import com.kostenko.webmydictionary.dao.domain.users.User;
import com.kostenko.webmydictionary.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rest/users")
public class UserRest {
    private static final Logger LOG = LoggerFactory.getLogger(UserRest.class);
    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getUsers() {
        LOG.debug("in get all users");
        return userService.findAll();
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.GET)
    public User getUser(@PathVariable(value = "login") String login) {
        LOG.debug("in get concrete user. login=" + login);
        return userService.findByLogin(login);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User newUser) {
        LOG.debug("in put. id=" + id);
        User another = userService.findByEmail(newUser.getEmail());
        if (another != null && !another.getId().equals(id)
                && !another.getEmail().equals(newUser.getEmail())) {
            LOG.debug("in put. user with this email is exists" + another.toString() + " vs " + newUser.toString());
            newUser.setEmail(null);
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
        userService.update(newUser);
        LOG.debug("in put. user updated...");
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable(value = "login") String login) {
        userService.remove(userService.findByLogin(login));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        LOG.debug("in post");
        if (userService.findByLogin(newUser.getLogin()) != null) {
            newUser.setLogin(null);
            LOG.debug("in post. Login exists");
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
        if (userService.findByEmail(newUser.getEmail()) != null) {
            newUser.setEmail(null);
            LOG.debug("in post. Email exists");
            return new ResponseEntity<>(newUser, HttpStatus.BAD_REQUEST);
        }
        userService.create(newUser);
        LOG.debug("in post. All is good...F");
        return new ResponseEntity<>(userService.findByEmail(newUser.getEmail()), HttpStatus.OK);
    }
}
