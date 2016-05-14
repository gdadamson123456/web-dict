package com.kostenko.webmydictionary.dao.domain.dictionary;

import java.io.Serializable;


public class Unit implements Serializable {
    public static final String FIELD_SOURCE = "source";
    public static final String FIELD_TRANSLATION = "translation";
    public static final String FIELD_USER_TRANSLATION = "userTranslation";
    public static final String FIELD_COUNTER = "counter";

    private String source;
    private String translations;
    private String userTranslation;
    private long counter;

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

    public Unit(final String source, final String translations, final String userTranslation, final long counter) {
        this(source, translations, counter);
        this.userTranslation = userTranslation;
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

    public String getUserTranslation() {
        return userTranslation;
    }

    public void setUserTranslation(String userTranslation) {
        this.userTranslation = userTranslation;
    }

    public long getCounter() {
        return counter;
    }

    public void setCounter(long counter) {
        this.counter = counter;
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
