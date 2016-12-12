package com.kostenko.webmydictionary.services;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;

public interface TranslationService {
    Unit translate(final String from, final String to, final String unit);
}
