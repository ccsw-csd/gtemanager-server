package com.ccsw.gtemanager.blacklist;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.blacklist.model.Blacklist;

public interface BlacklistRepository extends CrudRepository<Blacklist, Long> {

    @EntityGraph(value = "blacklist-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    List<Blacklist> findAll(Specification<Blacklist> specification, Sort by);

    @EntityGraph(value = "blacklist-entity-graph", type = EntityGraph.EntityGraphType.LOAD)
    List<Blacklist> findAll(Specification<Blacklist> specification);

}
