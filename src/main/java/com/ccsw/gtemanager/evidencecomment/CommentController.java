package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceCommentDto;

public class CommentController {

	@Autowired
	private CommentService commentService;
	/*
	 * GET: Devuelve un comentario según su id
	@RequestMapping(path = "/comment", method = RequestMethod.GET)
	public List<CommentDto> findWithComments(@RequestParam(value = "id", required = false) Long idEvidence) {
		List<Comment> comments = commentService.findCommentsByEvidence(idEvidence);
		
		return BeanMapper.mapList(comment, CommentDto.class);
	}*/
}
