package com.kostenko.webmydictionary.controllers.users.utils.recaptcha;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Data
public class ReCaptchaCheckerResponse {
    @JsonProperty
    private Boolean success;
    @JsonProperty("error-codes")
    private List<String> errorCodes;
}