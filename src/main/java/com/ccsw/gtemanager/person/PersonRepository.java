package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {

	List<Person> findPersonBySAGA(String saga);

}
