package com.kostenko.webmydictionary.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class AppConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfig.class);

    enum Property {
        DB_HOST("OPENSHIFT_MONGODB_DB_HOST"),
        DB_PORT("OPENSHIFT_MONGODB_DB_PORT"),
        DB_NAME("OPENSHIFT_APP_NAME"),
        DB_USER("OPENSHIFT_MONGODB_DB_USERNAME"),
        DB_PASSWORD("OPENSHIFT_MONGODB_DB_PASSWORD");

        public final String value;

        Property(String value) {
            this.value = value;
        }
    }

    static String getProperty(Property property) {
        String value = System.getenv(property.value);
        LOGGER.debug(String.format("%s=%s", property.value, value));
        return value;
    }
}
