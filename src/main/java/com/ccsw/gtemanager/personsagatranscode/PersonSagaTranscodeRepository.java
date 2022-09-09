package com.ccsw.gtemanager.personsagatranscode;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.personsagatranscode.model.PersonSagaTranscode;

/**
 * PersonSagaTranscodeRepository: repositorio de datos de personas con códigos
 * saga modificados.
 */
@Repository
public interface PersonSagaTranscodeRepository extends JpaRepository<PersonSagaTranscode, Long> {

    /**
     * Obtener todos los registros de personas con nuevos códigos saga de la base de
     * datos.
     * 
     * Se hace uso de EntityGraph para optimización de la consulta.
     */
    @EntityGraph(value = "person_saga_transcode-entity-graph", type = EntityGraphType.LOAD)
    List<PersonSagaTranscode> findAll();
}
