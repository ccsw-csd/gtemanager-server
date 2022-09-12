package com.ccsw.gtemanager.comment;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.comment.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
	
}
