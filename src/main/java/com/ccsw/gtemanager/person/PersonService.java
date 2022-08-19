package com.ccsw.gtemanager.person;

import java.util.List;

import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonService: servicio de datos de personas.
 */
public interface PersonService {

	/**
	 * Obtener Person dado un ID.
	 * 
	 * @param id ID por el que buscar persona
	 * @return Person encontrado
	 */
	Person getById(Long id);

	/**
	 * Obtener listado de personas dado un código saga.
	 * 
	 * @param saga Código saga por el que buscar
	 * @return Listado de Person encontrados
	 */
	List<Person> getBySaga(String saga);

	/**
	 * Obtener listado de todas las personas en la base de datos.
	 * 
	 * @return Listad de Person
	 */
	List<Person> getPeople();
}
