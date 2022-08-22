package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

@Service
public class EvidenceViewServiceImpl implements EvidenceViewService {
	
	@Autowired
	EvidenceViewRepository evidenceViewRepository;
	
	@Override
	public List<EvidenceComment> findCommentsByEvidence(Long idEvidence) {
		return this.evidenceViewRepository.findCommentsByEvidence(idEvidence);
	}
}
