package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
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
}
