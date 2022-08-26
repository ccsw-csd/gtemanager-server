package com.ccsw.gtemanager.evidencecomment.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "evidence_comment")
public class EvidenceComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;

	@Column(name = "comment", nullable = false)
	private String comment;

	public Long getId() {
		return id;
	}

	public Person getPerson() {
		return person;
	}

	public String getComment() {
		return comment;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}