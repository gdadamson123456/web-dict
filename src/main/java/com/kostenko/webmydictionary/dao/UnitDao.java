package com.kostenko.webmydictionary.dao;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;

import java.util.List;

public interface UnitDao {
    void save(final Unit unit);

    void remove(final Unit unit);

    Unit findBySource(final String source);

    Unit findByTranslation(final String translation);

    List<Unit> findAll();

}
