package com.kostenko.webmydictionary.controllers;

import com.kostenko.webmydictionary.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Serializable;

@Slf4j
@Controller
public class ErrorController implements Serializable {
    private static final long serialVersionUID = -6460667958149653016L;

    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String login(@RequestParam("errorMessage") String errorMessage, Model model) {
        model.addAttribute(Constants.ERROR_MESSAGE, errorMessage);
        return Constants.View.ERROR;
    }
}
