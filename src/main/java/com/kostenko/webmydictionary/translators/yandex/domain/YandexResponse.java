package com.kostenko.webmydictionary.translators.yandex.domain;

import java.util.List;
import java.util.Map;

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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Map<String, Object> getAdditionalTranslations() {
        return additionalTranslations;
    }

    public void setAdditionalTranslations(Map<String, Object> additionalTranslations) {
        this.additionalTranslations = additionalTranslations;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
