package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	
	@Override
	public List<EvidenceComment> findAll() {
		
		return this.commentRepository.findAll();
		//.orElseThrow(EntityNotFoundException::new);
	}
}
