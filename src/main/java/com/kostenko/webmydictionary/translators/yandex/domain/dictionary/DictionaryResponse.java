package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryResponse {
    @JsonProperty("head")
    private Object head;
    @JsonProperty("def")
    private List<Definition> definitions;
}
