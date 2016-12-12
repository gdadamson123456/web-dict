package com.kostenko.webmydictionary.translators;

public interface TranslatorAPI<T> {
    T translate(String from, String to, String message);
}
