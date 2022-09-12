package com.ccsw.gtemanager.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;

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
	public static final Long PERSON_ID = 1L;
	public static final String NEW_COMMENT = "New";
	
	@Mock
	private CommentRepository commentRepository;

	@InjectMocks
	private CommentServiceImpl commentServiceImpl;
	
	@Mock
	private PersonService personService;
	
	@Test
	public void saveExistsCommentShouldUpdate() throws EntityNotFoundException {
		
		CommentDto commentDto = new CommentDto();
		PersonDto personDto = new PersonDto();
		
		personDto.setId(PERSON_ID);
		
		commentDto.setId(EXISTS_ID);
		commentDto.setPerson(personDto);
		commentDto.setComment(NEW_COMMENT);
		
		ArgumentCaptor<Comment> commentEntity = ArgumentCaptor.forClass(Comment.class);
		//Comment commentEntity = mock(Comment.class);
		Person personEntity = mock(Person.class);
		

		when(commentRepository.findById(EXISTS_ID)).thenReturn(Optional.of(commentEntity.getValue()));
		//when(commentRepository.findById(EXISTS_ID)).thenAnswer(e -> e.getArguments()[0]);
		//when(commentDto.getId()).thenReturn(EXISTS_ID);
		//when(commentRepository.findById(any())).thenReturn(Optional.of(commentEntity));
		when(personService.findById(any())).thenReturn(personEntity);
		//when(personRepository.findById(any())).thenReturn(Optional.of(personEntity));		

		this.commentServiceImpl.save(commentDto);
		
		verify(this.commentRepository).save(commentEntity.capture());
		//verify(this.commentRepository).save(commentEntity);
		
		assertEquals(NEW_COMMENT, commentEntity.getValue().getComment());
		//assertEquals(NEW_COMMENT, commentEntity.getComment());
	}
}
