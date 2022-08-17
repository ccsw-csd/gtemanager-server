package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidence.model.Evidence;

@Service
public class EvidenceServiceImpl implements EvidenceService {

	@Autowired
	EvidenceRepository evidenceRepository;
	
	@Override
	public List<Evidence> find(Long idGeography) {
		return this.evidenceRepository.find(idGeography);
	}
}
