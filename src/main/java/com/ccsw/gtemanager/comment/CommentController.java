package com.ccsw.gtemanager.comment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.evidence.EvidenceService;

@RequestMapping(value = "/comment")
@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public void save(@RequestBody CommentDto dto) throws EntityNotFoundException {
		System.out.println("controlador " + dto);
		this.commentService.save(dto);
	}
}
