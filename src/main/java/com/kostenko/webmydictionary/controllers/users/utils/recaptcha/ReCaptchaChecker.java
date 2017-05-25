package com.kostenko.webmydictionary.controllers.users.utils.recaptcha;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
public abstract class ReCaptchaChecker {
    public static ReCaptchaCheckerResponse checkReCaptcha(String secret, String response, String verifyUrl) {
        log.debug("secret: %s", secret);
        log.debug("response:  %s", response);
        log.debug("verifyUrl:  %s", verifyUrl);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("secret", secret);
        map.add("response", response);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(verifyUrl, map, ReCaptchaCheckerResponse.class);
    }
}