package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidencecomment.model.Comment;
import com.ccsw.gtemanager.evidencecomment.model.CommentDto;

@RequestMapping(value = "/comment")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	/*
	 * GET: Devuelve un comentario según su id
	 */	
	@RequestMapping(path = "", method = RequestMethod.GET)
	public CommentDto findCommentByPerson(@RequestParam(value = "id", required = true) Long person) {
		Comment comment = commentService.findByPersonId(person);
		return this.beanMapper.map(comment, CommentDto.class);
	}
}
