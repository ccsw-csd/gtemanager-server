package com.ccsw.gtemanager.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.PersonService;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.person.model.PersonDto;

@ExtendWith(MockitoExtension.class)
public class CommentTest {

    public static final Integer TOTAL_COMMENT = 1;
    public static final Long EXISTS_ID = 1L;
    public static final Long NOT_EXISTS_ID = 2L;
    public static final Long PERSON_ID = 1L;
    public static final String NEW_COMMENT = "New";

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentServiceImpl;

    @Mock
    private PersonService personService;

    @Test
    public void saveNotExistCommentShouldCreate() throws EntityNotFoundException {

        PersonDto personDto = new PersonDto();
        personDto.setId(PERSON_ID);

        CommentDto commentDto = new CommentDto();
        commentDto.setComment(NEW_COMMENT);
        commentDto.setPerson(personDto);

        ArgumentCaptor<Comment> commentEntityCaptor = ArgumentCaptor.forClass(Comment.class);

        when(personService.findById(PERSON_ID)).thenReturn(mock(Person.class));

        this.commentServiceImpl.save(commentDto);

        verify(this.commentRepository).save(commentEntityCaptor.capture());

        assertEquals(NEW_COMMENT, commentEntityCaptor.getValue().getComment());
        assertEquals("Desconocido", commentEntityCaptor.getValue().getLastEditAuthor());
        assertNotNull(commentEntityCaptor.getValue().getLastEditDate());
    }

    @Test
    public void saveExistCommentShouldUpdate() throws EntityNotFoundException {

        PersonDto personDto = new PersonDto();
        personDto.setId(PERSON_ID);

        CommentDto commentDto = new CommentDto();
        commentDto.setId(EXISTS_ID);
        commentDto.setComment(NEW_COMMENT);
        commentDto.setPerson(personDto);

        Comment commentEntity = mock(Comment.class);

        when(commentRepository.findById(EXISTS_ID)).thenReturn(Optional.of(commentEntity));
        when(personService.findById(PERSON_ID)).thenReturn(mock(Person.class));

        this.commentServiceImpl.save(commentDto);

        verify(this.commentRepository).save(commentEntity);
    }
}
