package com.kostenko.webmydictionary.translators.yandex.domain.dictionary;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DictionaryResponse {
    @JsonProperty("head")
    private Object head;
    @JsonProperty("def")
    private List<Definition> definitions;

    public Object getHead() {
        return head;
    }

    public void setHead(Object head) {
        this.head = head;
    }

    public List<Definition> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Definition> definitions) {
        this.definitions = definitions;
    }

    @Override
    public String toString() {
        String definition = "{ def: " + definitions + '}';
        return CollectionUtils.isEmpty(definitions) ? "" : definition;
    }
}
