package com.ccsw.gtemanager.person;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.person.model.Person;

@Service
@Transactional
public class DefaultPersonService implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Person getById(Long id) {
		return personRepository.getById(id);
	}

	@Override
	public List<Person> getBySaga(String saga) {
		return personRepository.findBySaga(saga);
	}

	@Override
	public List<Person> getPeople() {
		return (List<Person>) personRepository.findAll();
	}

}
