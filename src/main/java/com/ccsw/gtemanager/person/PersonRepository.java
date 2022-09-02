package com.ccsw.gtemanager.person;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.person.model.Person;

public interface PersonRepository extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {

}
