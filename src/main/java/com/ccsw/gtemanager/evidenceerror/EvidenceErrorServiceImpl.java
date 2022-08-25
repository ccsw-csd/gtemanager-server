package com.ccsw.gtemanager.evidenceerror;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;


@Service
@Transactional
public class EvidenceErrorServiceImpl implements EvidenceErrorService {

	@Autowired
	private EvidenceErrorRepository evidenceErrorRepository;

	@Override
	public List<EvidenceError> getAll() {
		return (List<EvidenceError>) evidenceErrorRepository.findAll();
	}

	@Override
	public void saveAll(List<EvidenceError> errors) {
		evidenceErrorRepository.saveAll(errors);
	}

	@Override
	public void clear() {
		evidenceErrorRepository.deleteAll();
	}

}
