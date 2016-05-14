package com.kostenko.webmydictionary.dao.implementation.users;

import com.googlecode.objectify.VoidWork;
import com.kostenko.webmydictionary.dao.RoleDao;
import com.kostenko.webmydictionary.dao.domain.users.Role;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.kostenko.webmydictionary.utils.Constants.Objectify.IS_EQUAL;

@Repository
public class ObjectifyRoleDao implements RoleDao, Serializable {
    private static final long serialVersionUID = 3893806746257241542L;

    @Override
    public void create(final Role role) {
        final List<Role> roles = ofy().load().type(Role.class).filter(Role.FIELD_NAME + IS_EQUAL, role.getName()).list();
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                if (CollectionUtils.isEmpty(roles)) {
                    ofy().save().entity(role).now();
                }
            }
        });
    }

    @Override
    public void update(final Role role) {
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy().save().entity(role).now();
            }
        });
    }

    @Override
    public void remove(final Role role) {
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy().delete().entity(role).now();
            }
        });

    }

    @Override
    public Role findByName(String name) {
        List<Role> roles = ofy().load().type(Role.class).filter(Role.FIELD_NAME + IS_EQUAL, name).list();
        return CollectionUtils.isNotEmpty(roles) ? roles.get(0) : null;
    }

    @Override
    public List<Role> findAll() {
        return Collections.unmodifiableList(ofy().load().type(Role.class).list());
    }

}
