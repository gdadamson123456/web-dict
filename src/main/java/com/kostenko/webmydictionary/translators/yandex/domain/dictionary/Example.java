package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Example {
    @JsonProperty("text")
    private String text;
    @JsonProperty("tr")
    private List<ExTranslation> exTranslations;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<ExTranslation> getExTranslations() {
        return exTranslations;
    }

    public void setExTranslations(List<ExTranslation> exTranslations) {
        this.exTranslations = exTranslations;
    }
}
