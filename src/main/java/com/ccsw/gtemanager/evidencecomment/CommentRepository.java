package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.evidencecomment.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	
	@EntityGraph(attributePaths = { "person" })
	Comment findByPersonId(Long personId);
}
