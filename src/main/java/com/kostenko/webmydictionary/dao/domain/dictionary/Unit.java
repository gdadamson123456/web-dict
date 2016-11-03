package com.kostenko.webmydictionary.dao.domain.dictionary;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "units")
public class Unit implements Serializable {
    @Id
    private String id;
    private String source;
    private String translation;
    private Map<String, Object> translationAdditional;
    private List<String> technologies;
    private String errorMessage;

    public Unit() {
    }

    public Unit(final String source, final String translation, final Map<String, Object> translationAdditional, final List<String> technologies) {
        this.source = source;
        this.translation = translation;
        this.translationAdditional = translationAdditional;
        this.technologies = technologies;
    }
}
