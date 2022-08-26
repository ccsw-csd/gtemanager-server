package com.ccsw.gtemanager.evidencecomment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceCommentDto;

@RequestMapping(value = "/comment")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	/*
	 * GET: Devuelve un comentario según su id
	 * */
	@RequestMapping(path = "/comment", method = RequestMethod.GET)
	public List<EvidenceCommentDto> findWithComments(@RequestParam(value = "id", required = false) Long idEvidence) {
		//List<EvidenceComment> comments = commentService.findCommentsByEvidence(idEvidence);
		List<EvidenceComment> comments = commentService.findAll();
		
		return beanMapper.mapList(comments, EvidenceCommentDto.class);
	}
}
