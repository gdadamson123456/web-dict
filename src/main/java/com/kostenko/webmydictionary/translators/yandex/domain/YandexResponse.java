package com.kostenko.webmydictionary.translators.yandex.domain;

public class YandexResponse {
    private int code;
    private String lang;
    private String translation;
    private String additionalTranslations;
    private String errorCode;

    public YandexResponse() {
    }

    public YandexResponse(int code, String lang, String translation, String additionalTranslations, String errorCode) {
        this.code = code;
        this.lang = lang;
        this.translation = translation;
        this.additionalTranslations = additionalTranslations;
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

    public String getAdditionalTranslations() {
        return additionalTranslations;
    }

    public void setAdditionalTranslations(String additionalTranslations) {
        this.additionalTranslations = additionalTranslations;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
