package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.person.model.Person;

@Repository
public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

	List<Evidence> findByPersonId(Person personId);
	
}
