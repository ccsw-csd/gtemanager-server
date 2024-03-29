package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.evidenceview.model.EvidenceView;

public interface EvidenceViewRepository extends CrudRepository<EvidenceView, Comment> {

    @EntityGraph(value = "evidence-view-graph", type = EntityGraph.EntityGraphType.LOAD)
    List<EvidenceView> findAll(Specification<EvidenceView> specification, Sort by);

    @EntityGraph(value = "evidence-view-graph", type = EntityGraph.EntityGraphType.LOAD)
    List<EvidenceView> findByPersonIdIn(List<Long> personList);
}