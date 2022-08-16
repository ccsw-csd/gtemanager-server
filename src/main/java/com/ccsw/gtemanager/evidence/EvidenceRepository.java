package com.ccsw.gtemanager.evidence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.Evidence;

@Repository
public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

}