package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Definition {
    @JsonProperty("text")
    private String originalText;
    @JsonProperty("pos")
    private String originalPosition;
    @JsonProperty("gen")
    private String originalGender;
    @JsonProperty("tr")
    private List<Translation> translations;

    public String getOriginalText() {
        return originalText;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public String getOriginalPosition() {
        return originalPosition;
    }

    public void setOriginalPosition(String originalPosition) {
        this.originalPosition = originalPosition;
    }

    public String getOriginalGender() {
        return originalGender;
    }

    public void setOriginalGender(String originalGender) {
        this.originalGender = originalGender;
    }

    public List<Translation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<Translation> translations) {
        this.translations = translations;
    }


}
