package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidenceview.model.EvidenceView;

public interface EvidenceViewRepository extends CrudRepository<EvidenceView, EvidenceComment> {
	
	List<EvidenceComment> findCommentsByEvidence(Long idEvidence);
}
