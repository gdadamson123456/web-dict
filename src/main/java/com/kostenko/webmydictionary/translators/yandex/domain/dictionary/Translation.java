package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation {
    @JsonProperty("text")
    private String translatedText;
    @JsonProperty("pos")
    private String translatedPosition;
    @JsonProperty("gen")
    private String translatedGender;
    @JsonProperty("syn")
    private List<Synonim> translatedSynonims;
    @JsonProperty("mean")
    private List<Meaning> translatedMeans;
    @JsonProperty("ex")
    private List<Example> translatedExamples;

    public String getTranslatedText() {
        return translatedText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public String getTranslatedPosition() {
        return translatedPosition;
    }

    public void setTranslatedPosition(String translatedPosition) {
        this.translatedPosition = translatedPosition;
    }

    public String getTranslatedGender() {
        return translatedGender;
    }

    public void setTranslatedGender(String translatedGender) {
        this.translatedGender = translatedGender;
    }

    public List<Synonim> getTranslatedSynonims() {
        return translatedSynonims;
    }

    public void setTranslatedSynonims(List<Synonim> translatedSynonims) {
        this.translatedSynonims = translatedSynonims;
    }

    public List<Meaning> getTranslatedMeans() {
        return translatedMeans;
    }

    public void setTranslatedMeans(List<Meaning> translatedMeans) {
        this.translatedMeans = translatedMeans;
    }

    public List<Example> getTranslatedExamples() {
        return translatedExamples;
    }

    public void setTranslatedExamples(List<Example> translatedExamples) {
        this.translatedExamples = translatedExamples;
    }

    @Override
    public String toString() {
        String toString = "{text: %s, pos: %s, gen: %s, syn: %s, mean: %s, ex: %s}";
        return String.format(toString,
                isEmpty(translatedText) ? "" : translatedText,
                isEmpty(translatedPosition) ? "" : translatedPosition,
                isEmpty(translatedGender) ? "" : translatedGender,
                CollectionUtils.isEmpty(translatedSynonims) ? "" : translatedSynonims,
                CollectionUtils.isEmpty(translatedMeans) ? "" : translatedMeans,
                CollectionUtils.isEmpty(translatedExamples) ? "" : translatedExamples);
    }
}
