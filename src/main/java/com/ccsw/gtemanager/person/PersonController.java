package com.ccsw.gtemanager.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.person.model.PersonDto;

@RequestMapping(value = "/person")
@RestController
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	/*
	 * GET: Devuelve un comentario según su id
	 */	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public PersonDto findCommentByPerson(@RequestParam(value = "id", required = true) Long id) throws EntityNotFoundException {
		
		Person person = personService.findById(id);
		return this.beanMapper.map(person, PersonDto.class);
	}
}
