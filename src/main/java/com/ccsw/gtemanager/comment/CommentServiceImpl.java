package com.ccsw.gtemanager.comment;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.config.security.UserUtils;
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
        } else {
            comment = this.get(dto.getId());
        }

        comment.setComment(dto.getComment());
        comment.setPerson(personService.findById(dto.getPerson().getId()));
        comment.setLastEditDate(new Date());

        String author = null;

        try {
            author = UserUtils.getUserDetails().getDisplayName();
        } catch (Exception e) {

        }

        if (StringUtils.hasText(author) == false) {
            author = "Desconocido";
        }

        comment.setLastEditAuthor(author);

        this.commentRepository.save(comment);
    }

    @Override
    public void clear() {
        commentRepository.deleteAllInBatch();
    }
}
