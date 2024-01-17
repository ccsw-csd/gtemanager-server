package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonRepository: repositorio de datos de personas.
 */
@Repository
public interface PersonRepository extends CrudRepository<Person, Long>, JpaSpecificationExecutor<Person> {

    /**
     * Obtener todas las personas de la base de datos.
     * 
     * Se hace uso de EntityGraph para optimización de la consulta.
     */
    @Override
    @EntityGraph(value = "person-entity-graph", type = EntityGraphType.LOAD)
    List<Person> findAll();

    @EntityGraph(value = "person-entity-graph", type = EntityGraphType.LOAD)
    Person findByEmail(String email);

    @EntityGraph(value = "person-entity-graph", type = EntityGraphType.LOAD)
    List<Person> findByNameAndLastName(String name, String lastName);
}
