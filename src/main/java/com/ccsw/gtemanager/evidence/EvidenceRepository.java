package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.gtemanager.evidence.model.Evidence;

public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

	List<Evidence> findAll(Specification<Evidence> specification, Sort by);
}