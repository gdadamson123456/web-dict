package com.kostenko.webmydictionary.dao.implementation.dictionary;

import com.googlecode.objectify.VoidWork;
import com.kostenko.webmydictionary.dao.UnitDao;
import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static com.kostenko.webmydictionary.utils.Constants.Objectify.IS_EQUAL;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Repository
public class ObjectifyUnitDao implements UnitDao, Serializable {

    @Override
    public void save(final Unit unit) {
        final Unit units = ofy().load().type(Unit.class).id(unit.getSource()).now();
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                if (units == null) {
                    ofy().save().entity(unit).now();
                } else {
                    if (isNotEqual(unit)) {
                        ofy().delete().entity(unit).now();
                        ofy().save().entity(unit).now();
                    }
                }
            }
        });
    }

    private boolean isNotEqual(Unit unit) {
        return !findBySource(unit.getSource()).equals(unit)
                && isNotEmpty(unit.getSource())
                && isNotEmpty(unit.getTranslations());
    }

    @Override
    public void remove(final Unit unit) {
        ofy().transact(new VoidWork() {
            @Override
            public void vrun() {
                ofy().delete().entity(unit).now();
            }
        });
    }

    @Override
    public Unit findBySource(final String source) {
        Unit unit = ofy().load().type(Unit.class).id(source).now();
        return unit;
    }

    @Override
    public Unit findByTranslation(final String translation) {
        List<Unit> units = ofy().load().type(Unit.class).filterKey(Unit.FIELD_TRANSLATION + IS_EQUAL, translation).list();
        return CollectionUtils.isNotEmpty(units) ? units.get(0) : null;
    }

    @Override
    public List<Unit> findAll() {
        return ofy().load().type(Unit.class).list();
    }
}
