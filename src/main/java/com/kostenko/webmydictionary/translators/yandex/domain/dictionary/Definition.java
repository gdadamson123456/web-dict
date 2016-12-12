package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
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
}
