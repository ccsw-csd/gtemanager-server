package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.criteria.SearchCriteria;
import com.ccsw.gtemanager.evidenceview.EvidenceViewSpecification;
import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidenceview.model.EvidenceView;

@Service
public class EvidenceViewServiceImpl implements EvidenceViewService {
	
	@Autowired
	EvidenceViewRepository evidenceViewRepository;
	
	@Override
	public List<EvidenceView> findOrderedByGeography(Long idGeography) {
		
		EvidenceViewSpecification geography = new EvidenceViewSpecification(new SearchCriteria("center", ":", idGeography));
		Specification<EvidenceView> specification = Specification.where(geography);
		List<EvidenceView> list = this.evidenceViewRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "evidence.id"));
		System.out.println("AQUI ESTÁ: " + list);
		return list;
	}	
	
	@Override
	public List<EvidenceComment> findCommentsByEvidence(Long idEvidence) {
		return this.evidenceViewRepository.findCommentsByEvidence(idEvidence);
	}
}
