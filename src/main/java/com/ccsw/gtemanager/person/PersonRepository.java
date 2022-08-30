package com.ccsw.gtemanager.person;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonRepository: repositorio de datos de personas.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
