package com.ccsw.gtemanager.person;

import java.util.List;

import com.ccsw.gtemanager.person.model.Person;

public interface PersonService {
	
	Person getById(Long id);
	
	List<Person> getBySaga(String saga);
	
	List<Person> getPeople();
}
