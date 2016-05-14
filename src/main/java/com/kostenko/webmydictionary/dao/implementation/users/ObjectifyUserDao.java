package com.kostenko.webmydictionary.dao.implementation.users;

import com.googlecode.objectify.VoidWork;
import com.kostenko.webmydictionary.dao.UserDao;
import com.kostenko.webmydictionary.dao.domain.users.User;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.kostenko.webmydictionary.utils.Constants.Objectify.IS_EQUAL;

@Repository
public class ObjectifyUserDao implements UserDao, Serializable {
    private static final long serialVersionUID = -3225032021932775475L;


    @Override
    public void create(final User user) {
        final List<User> users = ofy().load().type(User.class).filter(User.FIELD_LOGIN + IS_EQUAL, user.getLogin()).list();
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                if (CollectionUtils.isEmpty(users)) {
                    ofy().save().entity(user).now();
                }
            }
        });
    }

    @Override
    public void update(final User user) {
        final List<User> users = ofy().load().type(User.class).filter(User.FIELD_LOGIN + IS_EQUAL, user.getLogin()).list();
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                if (CollectionUtils.isNotEmpty(users)) {
                    ofy().delete().entity(user).now();
                }
                ofy().save().entity(user).now();
            }
        });
    }

    @Override
    public void remove(final User user) {
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy().delete().entity(user).now();
            }
        });
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(ofy().load().type(User.class).list());
    }

    @Override
    public User findByLogin(final String login) {
        List<User> users = ofy().load().type(User.class).filter(User.FIELD_LOGIN + IS_EQUAL, login).list();
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

    @Override
    public User findById(final Long id) {
        return ofy().load().type(User.class).id(id).now();
    }

    @Override
    public User findByEmail(final String email) {
        List<User> users = ofy().load().type(User.class).filter(User.FIELD_EMAIL + IS_EQUAL, email).list();
        return CollectionUtils.isNotEmpty(users) ? users.get(0) : null;
    }

}
