package com.kostenko.webmydictionary.dao.implementation.dictionary;

import com.kostenko.webmydictionary.dao.UnitDao;
import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Repository
public class ObjectifyUnitDao implements UnitDao, Serializable {

    @Override
    public void save(final Unit unit) {

    }


    @Override
    public void remove(final Unit unit) {

    }

    @Override
    public Unit findBySource(final String source) {

        return null;
    }

    @Override
    public Unit findByTranslation(final String translation) {
        return null;
    }

    @Override
    public List<Unit> findAll() {
        return Collections.emptyList();
    }
}
