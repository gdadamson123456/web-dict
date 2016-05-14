package com.kostenko.webmydictionary.translators.yandex;

public enum Statuses {
    OK(200, "Success"),
    WRONG_API_KEY(401, "Wrong API Key"),
    BLOCKED_API_KEY(404, "API Key has blocked"),
    DAY_LIMIT(413, "The day limit of translation is exceeded"),
    WRONG_TEXT_SIZE(422, "Max text size is exceeded"),
    WRONG_TRANSLATION_DIRECTION(501, "This direction of translation is wrong or api doesn't support it");

    private String message;
    private int code;

    Statuses(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }
}
