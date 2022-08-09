package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.EvidenceType;

@Repository
public interface EvidenceTypeRepository extends CrudRepository<EvidenceType, Long> {

	List<EvidenceType> findByCode(String code);
	
}
