package com.ccsw.gtemanager.evidence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.EvidenceError;

@Repository
public interface EvidenceErrorRepository extends CrudRepository<EvidenceError, Long> {

}
