package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.criteria.SearchCriteria;
import com.ccsw.gtemanager.evidence.model.Evidence;

@Service
public class EvidenceServiceImpl implements EvidenceService {

	@Autowired
	EvidenceRepository evidenceRepository;
	
	@Override
	public List<Evidence> findOrderedByGeography(Long idGeography) {
		
		EvidenceSpecification geography = new EvidenceSpecification(new SearchCriteria("center", ":", idGeography));
		Specification<Evidence> specification = Specification.where(geography);
		List<Evidence> list = this.evidenceRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "person"));
		return list;
	}	
}
