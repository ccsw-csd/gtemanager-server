package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.evidencecomment.model.Comment;

public interface CommentService {
	
	Comment findByPersonId(Long personId);
}
