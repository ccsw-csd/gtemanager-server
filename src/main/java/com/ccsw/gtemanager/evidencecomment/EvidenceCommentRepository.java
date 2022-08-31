package com.ccsw.gtemanager.evidencecomment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

/**
 * EvidenceCommentRepository: repositorio de datos de comentarios de evidencias.
 */
@Repository
public interface EvidenceCommentRepository extends JpaRepository<EvidenceComment, Long> {

}
