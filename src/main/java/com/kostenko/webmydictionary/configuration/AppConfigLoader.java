package com.kostenko.webmydictionary.configuration;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@Slf4j
public class AppConfigLoader {
    private final Environment environment;

    @Autowired
    public AppConfigLoader(Environment environment) {
        this.environment = environment;
    }

    public String getProperty(Property property) {
        String value = Optional.fromNullable(System.getenv(property.value)).or(environment.getProperty(property.value));
        log.debug(String.format("%s=%s", property.value, value));
        Preconditions.checkNotNull(value, "property can't be null, please check configuration");
        Preconditions.checkArgument(StringUtils.isNotBlank(value), "property can't has empty value, please check configuration");
        return value;
    }

    public enum Property {
        DB_HOST("OPENSHIFT_MONGODB_DB_HOST"),
        DB_PORT("OPENSHIFT_MONGODB_DB_PORT"),
        DB_NAME("OPENSHIFT_APP_NAME"),
        DB_USER("OPENSHIFT_MONGODB_DB_USERNAME"),
        DB_PASSWORD("OPENSHIFT_MONGODB_DB_PASSWORD"),
        RECAPTCHA_KEY_PRIVATE("reCaptcha.key.private"),
        RECAPTCHA_VERIFY_URL("reCaptcha.verify.url"),
        YANDEX_API_TRANSLATION_KEY("yandex.api.translation.key"),
        YANDEX_API_TRANSLATION_URL("yandex.api.translation.url"),
        YANDEX_API_DICTIONARY_KEY("yandex.api.dictionary.key"),
        YANDEX_API_DICTIONARY_URL("yandex.api.dictionary.url");

        public final String value;

        Property(String value) {
            this.value = value;
        }
    }
}
