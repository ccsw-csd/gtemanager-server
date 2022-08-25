package com.ccsw.gtemanager.evidencetype;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;


@Service
@Transactional
public class EvidenceTypeServiceImpl implements EvidenceTypeService {

	@Autowired
	private EvidenceTypeRepository evidenceTypeRepository;

	@Override
	public List<EvidenceType> getAll() {
		return (List<EvidenceType>) evidenceTypeRepository.findAll();
	}

	@Override
	public EvidenceType getByCode(String code) {
		List<EvidenceType> types = evidenceTypeRepository.findByCodeIgnoreCase(code);
		return types.isEmpty() ? null : types.get(0);
	}

}
