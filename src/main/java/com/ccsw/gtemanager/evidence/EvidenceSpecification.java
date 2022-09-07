package com.ccsw.gtemanager.evidence;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.common.criteria.SearchCriteria;
import com.ccsw.gtemanager.evidence.model.Evidence;

/**
 * TODO DOCS
 *
 */
public class EvidenceSpecification implements Specification<Evidence> {

    private SearchCriteria searchCriteria;

    public EvidenceSpecification(SearchCriteria searchCriteria) {
        this.searchCriteria = searchCriteria;
    }

    @Override
    public Predicate toPredicate(Root<Evidence> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (searchCriteria.getValue() == null)
            return criteriaBuilder.like(root.join("personId").get("name"), "%");
        else
            return criteriaBuilder.equal(root.join("personId").get(searchCriteria.getKey()), searchCriteria.getValue());
    }

}
