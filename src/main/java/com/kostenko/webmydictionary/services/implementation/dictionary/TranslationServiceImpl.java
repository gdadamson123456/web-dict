package com.kostenko.webmydictionary.services.implementation.dictionary;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.services.TranslationService;
import com.kostenko.webmydictionary.services.UnitService;
import com.kostenko.webmydictionary.translators.TranslatorAPI;
import com.kostenko.webmydictionary.translators.yandex.Statuses;
import com.kostenko.webmydictionary.translators.yandex.domain.YandexResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service(value = "translationService")
public class TranslationServiceImpl implements TranslationService {
    private final UnitService unitService;
    private final TranslatorAPI<YandexResponse> translatorAPI;

    @Autowired
    public TranslationServiceImpl(TranslatorAPI<YandexResponse> translatorAPI, UnitService unitService) {
        checkNotNull(translatorAPI, "TranslatorAPI can't be null");
        checkNotNull(unitService, "UnitService can't be null");
        this.translatorAPI = translatorAPI;
        this.unitService = unitService;
    }

    @Override
    public Unit translate(final String from, final String to, final String unit) {
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

    private Unit getTranslation(final String from, final String to, final String source) {
        Unit result = null;
        YandexResponse translation = translatorAPI.translate(from, to, source);
        if (translation != null && Statuses.getStatus(translation.getCode()) == Statuses.OK) {
            result = new Unit(source, translation.getTranslation(), translation.getAdditionalTranslations(), translation.getTechnologies());
        } else if (translation != null) {
            result = new Unit();
            result.setErrorMessage(translation.getErrorCode());
        }
        return result;
    }

    private void createUnit(final Unit result) {
        if (result != null && StringUtils.isEmpty(result.getErrorMessage())) {
            unitService.save(result);
        }
    }
}