package com.ccsw.gtemanager.evidenceerror;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceErrorDto;

/**
 * EvidenceErrorServiceImpl: clase de implementación de EvidenceErrorService.
 */
@Service
@Transactional
public class EvidenceErrorServiceImpl implements EvidenceErrorService {

    @Autowired
    private EvidenceErrorRepository evidenceErrorRepository;

    @Autowired
    private BeanMapper beanMapper;

    @Override
    public List<EvidenceErrorDto> getEvidenceErrors() {
        return beanMapper.mapList(evidenceErrorRepository.findAll(), EvidenceErrorDto.class);
    }

    @Override
    public void saveAll(List<EvidenceErrorDto> errors) {
        evidenceErrorRepository.saveAll(beanMapper.mapList(errors, EvidenceError.class));
    }

    @Override
    public void clear() {
        evidenceErrorRepository.deleteAllInBatch();
    }

}
