package com.kostenko.webmydictionary.controllers;

import com.googlecode.objectify.ObjectifyService;
import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.dao.domain.users.Role;
import com.kostenko.webmydictionary.dao.domain.users.User;

public class ObjectifyInitialization {
    static {
        ObjectifyService.register(Role.class);
        ObjectifyService.register(User.class);
        ObjectifyService.register(Unit.class);
    }
}
