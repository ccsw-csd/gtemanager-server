package com.ccsw.gtemanager.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.model.Person;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	PersonRepository personRepository;
	
	@Override
	public Person findById(Long id) throws EntityNotFoundException {
		return this.personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}
}
