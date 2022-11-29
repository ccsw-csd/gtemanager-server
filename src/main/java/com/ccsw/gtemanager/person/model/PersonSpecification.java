package com.ccsw.gtemanager.person.model;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.common.criteria.TernarySearchCriteria;

public class PersonSpecification implements Specification<Person> {

    private static final long serialVersionUID = 1L;

    private final TernarySearchCriteria criteria;

    public PersonSpecification(TernarySearchCriteria criteria) {

        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }

        else if (criteria.getOperation().equalsIgnoreCase("concat concat :") && criteria.getValue() != null) {
            Expression<String> exp = builder.concat(root.<String>get(criteria.getKey()), " ");
            exp = builder.concat(exp, root.<String>get(criteria.getKey2()));
            exp = builder.concat(exp, " ");
            exp = builder.concat(exp, root.<String>get(criteria.getKey3()));
            return builder.like(exp, "%" + criteria.getValue() + "%");
        }

        return null;
    }
}
