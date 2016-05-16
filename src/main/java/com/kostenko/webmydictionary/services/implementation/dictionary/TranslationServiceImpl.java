package com.kostenko.webmydictionary.services.implementation.dictionary;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.services.TranslationService;
import com.kostenko.webmydictionary.services.UnitService;
import com.kostenko.webmydictionary.translators.TranslatorAPI;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "translationService")
public class TranslationServiceImpl implements TranslationService {
    @Autowired
    private UnitService unitService;
    @Autowired
    private TranslatorAPI<List<String>> translatorAPI;

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
        List<String> translations = translatorAPI.translate(from, to, unit);
        if (CollectionUtils.isNotEmpty(translations)) {
            StringBuilder builder = new StringBuilder();
            for (String translation : translations) {
                builder.append(translation);
                builder.append("\n");
            }
            result = new Unit(unit, builder.toString());
        }
        return result;
    }

    private void createUnit(Unit result) {
        if (result != null) {
            unitService.save(result);
        }
    }
}