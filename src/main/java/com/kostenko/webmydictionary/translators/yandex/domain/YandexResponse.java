package com.kostenko.webmydictionary.translators.yandex.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@Slf4j
@Data
public class YandexResponse {
    private int code;
    private String lang;
    private String translation;
    private Map<String, Object> additionalTranslations;
    private List<String> technologies;
    private String errorCode;

    public YandexResponse() {
    }

    public YandexResponse(int code, String lang, String translation, Map<String, Object> additionalTranslations, List<String> technologies, String errorCode) {
        this.code = code;
        this.lang = lang;
        this.translation = translation;
        this.additionalTranslations = additionalTranslations;
        this.technologies = technologies;
        this.errorCode = errorCode;
    }
}
