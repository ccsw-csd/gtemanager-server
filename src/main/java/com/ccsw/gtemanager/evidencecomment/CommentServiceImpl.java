package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.evidencecomment.model.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	@Override
	public Comment findByPersonId(Long personId) {
		return this.commentRepository.findByPersonId(personId);
	}
}
