package com.ccsw.gtemanager.person;

import java.util.List;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonService: servicio de datos de personas.
 */
public interface PersonService {

    /**
     * Obtener una persona según su id
     * 
     * @param id
     * @return
     * @throws EntityNotFoundException
     */
    Person findById(Long id) throws EntityNotFoundException;

    /**
     * Obtener una persona según su email
     * 
     * @param email
     * @return
     * @throws EntityNotFoundException
     */
    Person findByEmail(String email);

    /**
     * Obtener listado de todas las personas en la base de datos.
     * 
     * @return Listado de Person
     */
    List<Person> getPeople();

    /**
     * Leer y deducir código saga de la persona implicada.
     * 
     * @param saga Código a procesar
     * @return Código truncado y validado en formato numérico, o en longitud de 4
     *         caracteres alfanuméricos
     * @throws IllegalArgumentException No se ha introducido un código admisible
     */
    String parseSaga(String saga) throws IllegalArgumentException;

    List<Person> findFirst15Filter(String filter);

    Person getByNameLastName(String name, String lastName);
}
