package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

public interface CommentService {
	//EvidenceComment findCommentsByEvidence(Long idEvidence) throws EntityNotFoundException;
	
	List<EvidenceComment> findAll();
}
