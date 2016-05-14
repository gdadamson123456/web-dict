package com.kostenko.webmydictionary.controllers.users.utils.recaptcha;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class ReCaptchaChecker {
    public static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public static ReCaptchaCheckerResponse checkReCaptcha(String secret, String response) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secret);
        map.add("response", response);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(RECAPTCHA_VERIFY_URL, map, ReCaptchaCheckerResponse.class);
    }
}