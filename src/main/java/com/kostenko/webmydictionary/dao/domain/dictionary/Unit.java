package com.kostenko.webmydictionary.dao.domain.dictionary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public Map<String, Object> getTranslationAdditional() {
        return translationAdditional;
    }

    public void setTranslationAdditional(Map<String, Object> translationAdditional) {
        this.translationAdditional = translationAdditional;
    }

    public List<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(List<String> technologies) {
        this.technologies = technologies;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Unit unit = (Unit) o;
        return source.equals(unit.source) && (translation != null ? translation.equals(unit.translation) : unit.translation == null);

    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }
}
