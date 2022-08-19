package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonRepository: repositorio de datos de personas.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

	/**
	 * Obtener personas dado un código saga concreto. Se realiza un JOIN con
	 * PersonSagaTranscode para la resolución de códigos saga no incluidos en
	 * Person.
	 * 
	 * @param saga Código saga a buscar
	 * @return Listado de Person encontrados
	 */
	@Query("SELECT p FROM Person p LEFT JOIN PersonSagaTranscode s ON p.id = s.personId WHERE p.saga = :saga OR s.saga = :saga")
	List<Person> findBySaga(String saga);

}
