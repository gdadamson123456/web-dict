package com.kostenko.webmydictionary.configuration;

import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigLoader.class);

    public enum Property {
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

    public static String getProperty(Property property) {
        String value = System.getenv(property.value);
        LOGGER.debug(String.format("%s=%s", property.value, value));
        Preconditions.checkNotNull(value, "property can't be null, please check configuration");
        Preconditions.checkArgument(StringUtils.isNotBlank(value), "property can't has empty value, please check configuration");
        return value;
    }
}
