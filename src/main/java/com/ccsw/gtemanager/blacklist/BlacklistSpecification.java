package com.ccsw.gtemanager.blacklist;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.blacklist.model.Blacklist;
import com.ccsw.gtemanager.common.criteria.SearchCriteria;

public class BlacklistSpecification implements Specification<Blacklist> {

    private SearchCriteria criteria;

    public BlacklistSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Blacklist> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getKey().equals("year")) {
            return builder.equal(builder.function("YEAR", Integer.class, root.get("date")), criteria.getValue());
        }

        if (criteria.getKey().equals("month")) {
            return builder.equal(builder.function("MONTH", Integer.class, root.get("date")), criteria.getValue());
        }

        if (criteria.getKey().equals("center") && criteria.getValue() != null) {
            In<Long> inClause = builder.in(root.join("person").get("center"));
            for (String id : ((String) criteria.getValue()).split(",")) {
                inClause.value(Long.parseLong(id));
            }

            return inClause;
        }

        return builder.like(root.join("person").get("name"), "%");
    }
}