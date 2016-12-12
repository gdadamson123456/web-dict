package com.kostenko.webmydictionary.rest;

import com.kostenko.webmydictionary.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(Constants.Api.Rest.PROPERTIES)

public class PropInfoRestController {

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public ResponseEntity<String> getSystemInfo() {
        StringBuilder builder = new StringBuilder();
        String version = System.getProperty("java.version");
        builder.append(String.format("Java version=%s\n", version));
        builder.append("Env properties:\n");
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            builder.append(String.format("%s=%s\n", entry.getKey(), entry.getValue()));
        }
        builder.append("System properties:\n");
        for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
            builder.append(String.format("%s=%s\n", entry.getKey(), entry.getValue()));
        }
        return new ResponseEntity<>(builder.toString(), HttpStatus.OK);
    }
}
