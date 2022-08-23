package com.ccsw.gtemanager.evidence;

import java.util.List;

import com.ccsw.gtemanager.evidence.model.Evidence;

public interface EvidenceService {
	
	List<Evidence> findOrderedByGeography(Long idGeography);
}
