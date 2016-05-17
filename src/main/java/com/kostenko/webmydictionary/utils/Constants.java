package com.kostenko.webmydictionary.utils;

public final class Constants {
    public static final String ERROR_MESSAGE = "errorMessage";
    public static final String MODE = "mode";
    public static final String MODEL_EDIT_FORM = "editForm";
    public static final String ROLE_ADMIN = "adminq";
    public static final String ROLE_USER = "user";

    public final class View {
        public static final String ADMIN = "AdminView";
        public static final String EDIT_USER = "EditUserView";
        public static final String ERROR = "ErrorView";
        public static final String LOGIN = "LoginView";
        public static final String USER = "UserView";

        public final class RedirectionTo {
            public static final String ERROR = "redirect:/error";
            public static final String LOGIN = "redirect:/login";
            public static final String ADMIN = "redirect:/admin";
            public static final String USER = "redirect:/user";
        }
    }
}
