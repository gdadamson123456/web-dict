package com.kostenko.webmydictionary.translators.yandex.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslationResponse {
    @JsonProperty("code")
    private int code;
    @JsonProperty("lang")
    private String lang;
    @JsonProperty("text")
    private List<String> text;
    @JsonProperty("errorCode")
    private String errorCode;
}
