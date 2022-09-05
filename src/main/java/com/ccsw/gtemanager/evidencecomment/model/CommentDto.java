package com.ccsw.gtemanager.evidencecomment.model;

import com.ccsw.gtemanager.person.model.PersonDto;

public class CommentDto {
	
	private Long id;
	
	private PersonDto person;
	
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PersonDto getPerson() {
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
