package com.kostenko.webmydictionary.services.implementation.dictionary;

import com.kostenko.webmydictionary.dao.UnitRepository;
import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.services.UnitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service(value = "unitService")
public class UnitServiceImpl implements UnitService {
    private final UnitRepository unitRepository;

    @Autowired
    public UnitServiceImpl(UnitRepository unitRepository) {
        checkNotNull(unitRepository, "UnitRepository can't be null");
        this.unitRepository = unitRepository;
    }

    @Override
    public void save(Unit unit) {
        unitRepository.save(unit);
    }

    @Override
    public void remove(Unit unit) {
        unitRepository.delete(unit);
    }

    @Override
    public Unit findBySource(String source) {
        return unitRepository.findBySource(source);
    }

    @Override
    public Unit findByTranslation(String translation) {
        return unitRepository.findByTranslation(translation);
    }

    @Override
    public List<Unit> findAll() {
        return unitRepository.findAll();
    }
}
