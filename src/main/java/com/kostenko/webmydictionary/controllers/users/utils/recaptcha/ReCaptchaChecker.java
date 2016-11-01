package com.kostenko.webmydictionary.controllers.users.utils.recaptcha;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public abstract class ReCaptchaChecker {
    public static ReCaptchaCheckerResponse checkReCaptcha(String secret, String response, String verifyUrl) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secret);
        map.add("response", response);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(verifyUrl, map, ReCaptchaCheckerResponse.class);
    }
}