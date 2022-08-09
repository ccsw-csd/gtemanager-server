package com.ccsw.gtemanager.evidence;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.UploadDto;

@Service
@Transactional
public class DefaultEvidenceService implements EvidenceService {

	@Override
	public void uploadEvidence(UploadDto upload) throws IllegalArgumentException {

	}

	@Override
	public List<Evidence> getEvidences() {
		return null;
	}

	@Override
	public List<EvidenceError> getEvidenceErrors() {
		return null;
	}

	@Override
	public List<EvidenceComment> getEvidenceComments() {
		return null;
	}

	@Override
	public String findWeekForPeriod(String period) throws IllegalArgumentException {
		return null;
	}

	@Override
	public List<EvidenceType> findEvidenceType(String type) {
		return null;
	}

	@Override
	public void emptyEvidences() {

	}

	@Override
	public void emptyErrors() {

	}

	@Override
	public void emptyComments() {

	}

}
