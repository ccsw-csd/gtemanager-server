package com.ccsw.gtemanager.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	List<Person> findBySaga(String saga);
}