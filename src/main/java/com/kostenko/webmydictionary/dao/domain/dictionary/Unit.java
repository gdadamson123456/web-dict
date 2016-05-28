package com.kostenko.webmydictionary.dao.domain.dictionary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "units")
public class Unit implements Serializable {
    @Id
    private String id;
    private String source;
    private String translation;
    private String translationAdditional;
    private String errorMessage;

    public Unit() {
    }

    public Unit(final String source, final String translation) {
        this.source = source;
        this.translation = translation;
    }

    public Unit(final String source, final String translation, final String translationAdditional) {
        this(source, translation);
        this.translationAdditional = translationAdditional;
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

    public String getTranslationAdditional() {
        return translationAdditional;
    }

    public void setTranslationAdditional(String translationAdditional) {
        this.translationAdditional = translationAdditional;
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
        if (!source.equals(unit.source)) {
            return false;
        }
        return translation != null ? translation.equals(unit.translation) : unit.translation == null;

    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + (translation != null ? translation.hashCode() : 0);
        return result;
    }
}
