package com.ccsw.gtemanager.evidencetype;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;

/**
 * EvidenceTypeServiceImpl: clase de implementación de EvidenceTypeService.
 */
@Service
@Transactional
public class EvidenceTypeServiceImpl implements EvidenceTypeService {
	
	@Autowired
	private EvidenceTypeRepository evidenceTypeRepository;

    @Override
    public List<EvidenceType> getEvidenceTypes() {
        return (List<EvidenceType>) evidenceTypeRepository.findAll();
    }
}
