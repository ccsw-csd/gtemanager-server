package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.Evidence;

/**
 * EvidenceRepository: repositorio de datos de evidencias. TODO DOCS
 */
@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long>, JpaSpecificationExecutor<Evidence> {

    /**
     * Obtener todas las evidencias de la base de datos.
     * 
     * Se hace uso de EntityGraph para optimización de la consulta.
     */
    @EntityGraph(value = "evidence-entity-graph", type = EntityGraphType.LOAD)
    List<Evidence> findAll();

    /**
     * TODO DOCS
     * 
     */
    @EntityGraph(value = "evidence-entity-graph", type = EntityGraphType.LOAD)
    List<Evidence> findAll(Specification<Evidence> specification);
}
