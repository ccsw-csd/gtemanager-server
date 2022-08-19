package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ccsw.gtemanager.person.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	@Query("SELECT p FROM Person p LEFT JOIN PersonSagaTranscode s ON p.id = s.personId WHERE p.saga = :saga OR s.saga = :saga")
	List<Person> findBySaga(String saga);

}
