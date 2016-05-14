package com.kostenko.webmydictionary.controllers.users.utils.recaptcha;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReCaptchaCheckerResponse {
    @JsonProperty
    private Boolean success;
    @JsonProperty("error-codes")
    private List<String> errorCodes;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(List<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}