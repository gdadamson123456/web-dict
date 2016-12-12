package com.kostenko.webmydictionary.dao;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UnitRepository extends MongoRepository<Unit, String> {
    Unit findBySource(final String source);

    Unit findByTranslation(final String translation);
}
