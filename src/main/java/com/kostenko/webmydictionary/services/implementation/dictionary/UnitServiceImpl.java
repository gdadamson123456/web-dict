package com.kostenko.webmydictionary.services.implementation.dictionary;

import com.kostenko.webmydictionary.dao.UnitDao;
import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("unitDao")
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitDao unitDao;

    @Override
    public void save(Unit unit) {
        unitDao.save(unit);
    }

    @Override
    public void remove(Unit unit) {
        unitDao.remove(unit);
    }

    @Override
    public Unit findBySource(String source) {
        return unitDao.findBySource(source);
    }

    @Override
    public Unit findByTranslation(String translation) {
        return unitDao.findByTranslation(translation);
    }

    @Override
    public List<Unit> findAll() {
        return unitDao.findAll();
    }
}
