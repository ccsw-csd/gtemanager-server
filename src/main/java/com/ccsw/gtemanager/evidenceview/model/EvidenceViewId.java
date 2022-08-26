package com.ccsw.gtemanager.evidenceview.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.person.model.Person;

@Embeddable
public class EvidenceViewId implements Serializable {
	
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
}
