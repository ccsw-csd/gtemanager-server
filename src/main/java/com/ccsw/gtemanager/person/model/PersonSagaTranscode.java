package com.ccsw.gtemanager.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "person_saga_transcode")
public class PersonSagaTranscode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person personId;

	@Column(name = "saga", nullable = false)
	private String saga;

	public Long getId() {
		return id;
	}

	public Person getPersonId() {
		return personId;
	}

	public String getSaga() {
		return saga;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public void setSaga(String saga) {
		this.saga = saga;
	}

}
