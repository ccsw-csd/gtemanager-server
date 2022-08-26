package com.ccsw.gtemanager.evidencetype;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;


@Service
public class EvidenceTypeServiceImpl implements EvidenceTypeService {
	
	@Autowired
	EvidenceTypeRepository evidenceTypeRepository;
	
	@Override
	public List<EvidenceType> findAll() {
		return this.evidenceTypeRepository.findAll();
	}
}
