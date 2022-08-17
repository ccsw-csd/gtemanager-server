package com.ccsw.gtemanager.user;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.common.criteria.TernarySearchCriteria;
import com.ccsw.gtemanager.user.model.UserEntity;

public class UserSpecification implements Specification<UserEntity> {

    private static final long serialVersionUID = 1L;
    private final TernarySearchCriteria criteria;

    public UserSpecification(TernarySearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<UserEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        if (criteria.getOperation().equalsIgnoreCase(":") && criteria.getValue() != null) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return criteriaBuilder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return criteriaBuilder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        } else if (criteria.getOperation().equalsIgnoreCase("concat :") && criteria.getValue() != null) {
            Expression<String> exp = criteriaBuilder.concat(root.<String>get(criteria.getKey()), " ");
            exp = criteriaBuilder.concat(exp, root.<String>get(criteria.getKey2()));
            return criteriaBuilder.like(exp, "%" + criteria.getValue() + "%");
        } else if (criteria.getOperation().equalsIgnoreCase("concat concat :") && criteria.getValue() != null) {
            Expression<String> exp = criteriaBuilder.concat(root.<String>get(criteria.getKey()), " ");
            exp = criteriaBuilder.concat(exp, root.<String>get(criteria.getKey2()));
            exp = criteriaBuilder.concat(exp, " ");
            exp = criteriaBuilder.concat(exp, root.<String>get(criteria.getKey3()));
            query.orderBy(criteriaBuilder.asc(root.<String>get(criteria.getKey())),
                    criteriaBuilder.desc(root.<String>get(criteria.getKey2())));
            return criteriaBuilder.like(exp, "%" + criteria.getValue() + "%");
        }
        return null;
    }

}