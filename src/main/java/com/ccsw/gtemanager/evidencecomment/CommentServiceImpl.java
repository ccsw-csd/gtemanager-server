package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	/*
	@Override
	public Comment findCommentsByEvidence(Long idEvidence) throws EntityNotFoundException {
		
		return this.commentRepository.findByEvidence(idEvidence).orElseThrow(EntityNotFoundException::new);
	}*/
}
