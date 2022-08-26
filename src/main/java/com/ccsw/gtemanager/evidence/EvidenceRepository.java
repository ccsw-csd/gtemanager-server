package com.ccsw.gtemanager.evidence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.Evidence;

/**
 * EvidenceRepository: repositorio de datos de evidencias.
 */
@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

}
