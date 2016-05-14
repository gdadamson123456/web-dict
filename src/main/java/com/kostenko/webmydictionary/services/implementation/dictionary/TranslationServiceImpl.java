package com.kostenko.webmydictionary.services.implementation.dictionary;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.services.TranslationService;
import com.kostenko.webmydictionary.services.UnitService;
import com.kostenko.webmydictionary.translators.TranslatorAPI;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("translationService")
public class TranslationServiceImpl implements TranslationService {
    @Autowired
    private UnitService unitService;
    @Autowired
    private TranslatorAPI<String> translatorAPI;

    @Override
    public Unit translate(String from, String to, String unit) {
        Unit result;
        Unit dataStoreUnit = unitService.findBySource(unit);
        if (dataStoreUnit != null) {
            result = dataStoreUnit;
        } else {
            result = getTranslation(from, to, unit);
            createUnit(result);
        }
        return result;
    }

    private Unit getTranslation(String from, String to, String unit) {
        Unit result = null;
        String translations = translatorAPI.translate(from, to, unit);
        if (StringUtils.isNotEmpty(translations)) {
            result = new Unit(unit, translations);
        }
        return result;
    }

    private void createUnit(Unit result) {
        if (result != null) {
            unitService.save(result);
        }
    }
}
