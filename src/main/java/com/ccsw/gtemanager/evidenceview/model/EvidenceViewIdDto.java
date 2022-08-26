package com.ccsw.gtemanager.evidenceview.model;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceDto;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceCommentDto;
import com.ccsw.gtemanager.person.model.PersonDto;

public class EvidenceViewIdDto {
	
	private Long id;
	
	private PersonDto person;

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
	
	
}
