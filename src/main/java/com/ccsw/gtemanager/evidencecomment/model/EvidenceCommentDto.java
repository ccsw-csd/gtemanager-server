package com.ccsw.gtemanager.evidencecomment.model;

import com.ccsw.gtemanager.person.model.Person;

public class EvidenceCommentDto {
	
	private Long id;
	
	private Person person;
	
	private String comment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
