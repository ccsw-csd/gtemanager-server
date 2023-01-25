package com.ccsw.gtemanager.evidenceerror;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;

/**
 * EvidenceErrorRepository: repositorio de datos de errores de evidencias.
 */
@Repository
public interface EvidenceErrorRepository extends JpaRepository<EvidenceError, Long> {

    List<EvidenceError> findByEmail(String email);

}
