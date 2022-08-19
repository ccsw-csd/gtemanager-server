package com.ccsw.gtemanager.evidence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.EvidenceComment;

/**
 * EvidenceCommentRepository: repositorio de datos de comentarios de evidencias.
 */
@Repository
public interface EvidenceCommentRepository extends CrudRepository<EvidenceComment, Long> {

}
