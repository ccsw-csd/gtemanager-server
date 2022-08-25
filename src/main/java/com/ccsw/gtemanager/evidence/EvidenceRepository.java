package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.evidence.model.Evidence;

public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

	@EntityGraph(attributePaths = { "person", "evidenceTypeW1", "evidenceTypeW2", "evidenceTypeW3", "evidenceTypeW4", "evidenceTypeW5", "evidenceTypeW6" })
	List<Evidence> findAll(Specification<Evidence> specification, Sort by);
}