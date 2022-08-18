package com.ccsw.gtemanager.evidence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.common.criteria.SearchCriteria;
import com.ccsw.gtemanager.evidence.model.Evidence;

public class EvidenceSpecification implements Specification<Evidence> {

	private SearchCriteria criteria;
	
	public EvidenceSpecification(SearchCriteria criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public Predicate toPredicate(Root<Evidence> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
		if (criteria.getValue() == null)
			return builder.like(root.join("person").get("name"), "%");
		return builder.equal(root.join("person").get(criteria.getKey()), criteria.getValue());
	}
	
}
