package com.ccsw.gtemanager.evidencecolor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/evidence-color")
@RestController
public class EvidenceColorController {

    @Autowired
    private EvidenceColorService evidenceColorService;

    @RequestMapping(path = "/{personId}/{rowColor}", method = RequestMethod.PUT)
    public void modifyColor(@PathVariable("personId") Long personId, @PathVariable("rowColor") String rowColor) {
        this.evidenceColorService.modifyColor(personId, rowColor);
    }
}
