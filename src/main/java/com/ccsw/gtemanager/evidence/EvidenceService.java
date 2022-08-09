package com.ccsw.gtemanager.evidence;

import java.util.List;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.UploadDto;

public interface EvidenceService {

	void uploadEvidence(UploadDto upload) throws IllegalArgumentException;
	
	List<Evidence> getEvidences();
	
	List<EvidenceError> getEvidenceErrors();
	
	List<EvidenceComment> getEvidenceComments();
	
	List<EvidenceType> findEvidenceType(String type);
	
	String findWeekForPeriod(String period) throws IllegalArgumentException; 
	
	void emptyEvidences();
	
	void emptyErrors();
	
	void emptyComments();
}
