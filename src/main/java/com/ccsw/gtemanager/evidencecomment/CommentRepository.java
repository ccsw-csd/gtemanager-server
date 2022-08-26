package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

public interface CommentRepository extends CrudRepository<EvidenceComment, Long> {
	
	@EntityGraph(attributePaths = { "person" })
	List<EvidenceComment> findAll();
}
