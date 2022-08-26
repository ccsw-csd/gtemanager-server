package com.ccsw.gtemanager.evidencetype;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;

public interface EvidenceTypeRepository extends CrudRepository<EvidenceType, Long> {

	List<EvidenceType> findAll();
}
