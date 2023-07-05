package com.ccsw.gtemanager.recurrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.recurrence.model.RecurrenceDto;

@RequestMapping(value = "/recurrence")
@RestController
public class RecurrenceController {

    @Autowired
    RecurrenceService recurrenceService;

    @RequestMapping(path = "", method = RequestMethod.PUT)
    public void save(@RequestBody RecurrenceDto dto) {
        this.recurrenceService.save(dto);
    }

}
