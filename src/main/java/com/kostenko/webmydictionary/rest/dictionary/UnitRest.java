package com.kostenko.webmydictionary.rest.dictionary;

import com.kostenko.webmydictionary.dao.domain.dictionary.Unit;
import com.kostenko.webmydictionary.services.TranslationService;
import com.kostenko.webmydictionary.services.UnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rest/unit")
public class UnitRest {
    private static final Logger LOG = LoggerFactory.getLogger(UnitRest.class);
    @Autowired
    private UnitService unitService;
    @Autowired
    private TranslationService translationService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Unit>> getUnits() {
        LOG.debug("in get all units");
        return new ResponseEntity<>(unitService.findAll(), HttpStatus.OK);
    }

//    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//    public ResponseEntity<Unit> getUnit(@PathVariable String id) {
//        LOG.debug("in getUnit. ID=" + id);
//        return new ResponseEntity<>(unitService.findBySource(id), HttpStatus.OK);
//    }

    @RequestMapping(value = "/{from}/{to}/{message}", method = RequestMethod.GET)
    public ResponseEntity<Unit> getTranslationOfUnit(@PathVariable String from, @PathVariable String to,
                                                     @PathVariable String message) {
        LOG.debug(String.format("in getTranslationOfUnit. /{%1$S}/{%2$S}/{%3$S}", from, to, message));
        return new ResponseEntity<>(translationService.translate(from, to, message), HttpStatus.OK);
    }
}
