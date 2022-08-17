package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.ccsw.gtemanager.evidence.model.Evidence;

public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

	@Query("select e from Evidence e where person_id in (select p.id from Person p where (:geography is null or p.center.id = :geography))")
	List<Evidence> find(@Param("geography") Long geography);
}