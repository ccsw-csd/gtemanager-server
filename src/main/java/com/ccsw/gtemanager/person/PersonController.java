package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.person.model.PersonDto;

@RequestMapping(value = "/person")
@RestController
@CrossOrigin(origins = "*")
public class PersonController {

    @Autowired
    BeanMapper beanMapper;

    @Autowired
    PersonService personService;

    @RequestMapping(path = "/{filter}", method = RequestMethod.GET)
    public List<PersonDto> findByFilter(@PathVariable String filter) {

        return this.beanMapper.mapList(this.personService.findAllPersonsFromFilters(filter), PersonDto.class);

    }

}
