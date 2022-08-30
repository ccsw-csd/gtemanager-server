package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

/**
 * EvidenceCommentServiceImpl: clase de implementación de
 * EvidenceCommentService.
 */
@Service
@Transactional
public class EvidenceCommentServiceImpl implements EvidenceCommentService {

    @Autowired
    private EvidenceCommentRepository evidenceCommentRepository;

    @Override
    public List<EvidenceComment> getEvidenceComments() {
        return evidenceCommentRepository.findAll();
    }

    @Override
    public void clear() {
        evidenceCommentRepository.deleteAllInBatch();
    }

}
