package com.kostenko.webmydictionary.dao.domain.dictionary;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "units")
public class Unit implements Serializable {
    public static final String FIELD_ID = "id";
    public static final String FIELD_SOURCE = "source";
    public static final String FIELD_TRANSLATION = "translation";
    public static final String FIELD_COUNTER = "counter";

    @Id
    private String id;
    private String source;
    private String translations;
    private long counter;
    private String errorMessage;

    public Unit() {
        this.counter = 0;
    }

    public Unit(final String source, final String translations) {
        this();
        this.source = source;
        this.translations = translations;
    }

    public Unit(final String source, final String translations, final long counter) {
        this.source = source;
        this.translations = translations;
        this.counter = counter;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTranslations() {
        return translations;
    }

    public void setTranslations(String translations) {
        this.translations = translations;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
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
        return translations != null ? translations.equals(unit.translations) : unit.translations == null;

    }

    @Override
    public int hashCode() {
        int result = source.hashCode();
        result = 31 * result + (translations != null ? translations.hashCode() : 0);
        return result;
    }
}
