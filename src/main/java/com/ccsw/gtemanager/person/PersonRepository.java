package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonRepository: repositorio de datos de personas.
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Obtener todas las personas de la base de datos.
     * 
     * Se hace uso de EntityGraph para optimización de la consulta.
     */
    @EntityGraph(value = "person-entity-graph", type = EntityGraphType.LOAD)
    List<Person> findAll();

    Person findByEmail(String email);
}
