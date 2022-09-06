package com.ccsw.gtemanager.evidence;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.evidence.model.Evidence;

/**
 * TODO DOCS
 *
 */
public class EvidenceSpecification {

    public static Specification<Evidence> whereCenter(Long centerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("personId").get("center"), centerId);
    }

}
