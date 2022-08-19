package com.ccsw.gtemanager.evidence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.EvidenceError;

/**
 * EvidenceErrorRepository: repositorio de datos de errores de evidencias.
 */
@Repository
public interface EvidenceErrorRepository extends CrudRepository<EvidenceError, Long> {

}
