package com.kostenko.webmydictionary.translators.yandex;

public enum Languages {
    AFRIKAANS("af"), ALBANIAN("sq"), ARABIC("ar"), ARMENIAN("hy"), AZERBAIJANI("az"),
    BASHKIR("ba"), BASQUE("eu"), BELARUSIAN("be"), BOSNIAN("bs"), BULGARIAN("bg"),
    CATALAN("ca"), CHINESE("zh"), CROATIAN("hr"), CZECH("cs"), DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESTONIAN("et"),
    FINNISH("fi"), FRENCH("fr"),
    GALICIAN("gl"), GEORGIAN("ka"), GERMAN("de"), GREEK("el"),
    HAITIAN("ht"), HE("Hebrew"), HINDI("hi"), HUNGARIAN("hu"),
    ICELANDIC("is"), INDONESIAN("id"), IRISH("ga"), ITALIAN("it"),
    JAPANESE("ja"),
    KAZAKH("kk"), KOREAN("ko"), KYRGYZ("ky"),
    LATIN("la"), LATVIAN("lv"), LITHUANIAN("lt"),
    MACEDONIAN("mk"), MALAGASY("mg"), MALAY("ms"), MALTESE("mt"), MONGOLIAN("mn"),
    NO("Norwegian"),
    PERSIAN("fa"), POLISH("pl"), PORTUGUESE("pt"), ROMANIAN("ro"), RUSSIAN("ru"),
    SERBIAN("sr"), SLOVAK("sk"), SLOVENIAN("sl"), SPANISH("es"), SWAHILI("sw"), SWEDISH("sv"),
    TAGALOG("tl"), TAJIK("tg"), TATAR("tt"), THAI("th"), TURKISH("tr"),
    UDMURT("udm"), UKRAINIAN("uk"), URDU("ur"), UZBEK("uz"),
    VIETNAMESE("vi"),
    WELSH("cy");

    private String code;

    Languages(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
