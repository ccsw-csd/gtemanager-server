package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

public interface EvidenceViewService {
	List<EvidenceComment> findCommentsByEvidence(Long idEvidence);
}
