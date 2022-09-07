package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsw.gtemanager.evidence.model.Evidence;

/**
 * EvidenceRepository: repositorio de datos de evidencias.
 */
@Repository
public interface EvidenceRepository extends JpaRepository<Evidence, Long> {

	@EntityGraph(attributePaths = { "person", "evidenceTypeW1", "evidenceTypeW2", "evidenceTypeW3", "evidenceTypeW4", "evidenceTypeW5", "evidenceTypeW6" })
	List<Evidence> findAll(Specification<Evidence> specification, Sort by);
}