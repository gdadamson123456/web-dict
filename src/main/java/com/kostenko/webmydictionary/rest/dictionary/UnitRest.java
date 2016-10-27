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

import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("/api/rest/units")
public class UnitRest {
    private static final Logger LOG = LoggerFactory.getLogger(UnitRest.class);
    private final UnitService unitService;
    private final TranslationService translationService;

    @Autowired
    public UnitRest(UnitService unitService, TranslationService translationService) {
        checkNotNull(unitService, "UnitService can't be null");
        checkNotNull(translationService, "TranslationService can't be null");
        this.unitService = unitService;
        this.translationService = translationService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Unit>> getUnits() {
        LOG.debug("in get all units");
        return new ResponseEntity<>(unitService.findAll(), HttpStatus.OK);
    }

    @RequestMapping(value = "/{source}", method = RequestMethod.GET)
    public ResponseEntity<Unit> getUnitBySource(@PathVariable String source) {
        LOG.debug("in getUnitBySource. source=" + source);
        return new ResponseEntity<>(unitService.findBySource(source), HttpStatus.OK);
    }

    @RequestMapping(value = "/{from}/{to}/{message}", method = RequestMethod.GET)
    public ResponseEntity<Unit> getTranslationOfUnit(@PathVariable String from, @PathVariable String to,
                                                     @PathVariable String message) {
        LOG.debug(String.format("in getTranslationOfUnit. /{%1$S}/{%2$S}/{%3$S}", from, to, message));
        Unit translate = translationService.translate(from, to, message);
        return new ResponseEntity<>(translate, HttpStatus.OK);
    }
}

