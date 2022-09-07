package com.ccsw.gtemanager.comment;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.PersonService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PersonService personService;
	
	@Override
    public List<Comment> getComments() {
        return commentRepository.findAll();
    }
	
	@Override
    public Comment get(Long id) throws EntityNotFoundException {
        return this.commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
	
	@Override
	public void save(CommentDto dto) throws EntityNotFoundException {
		Comment comment = null;
		
		if (dto.getId() == null) {
			comment = new Comment();
			System.out.println("nulo");
		} else {
			comment = this.get(dto.getId());	
		}
		
		System.out.println("comment: " + dto.getComment());
		comment.setComment(dto.getComment());
		System.out.println("person: " + dto.getPerson().getId());
		System.out.println("person: " + dto.getPerson().getName());
		comment.setPerson(personService.findById(dto.getPerson().getId()));
		
		this.commentRepository.save(comment);
	}

    @Override
    public void clear() {
        commentRepository.deleteAllInBatch();
    }
}
