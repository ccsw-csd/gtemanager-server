package com.ccsw.gtemanager.evidence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "evidence")
public class Evidence {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person personId;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w1", nullable = false)
	private EvidenceType evidenceTypeW1;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w2", nullable = false)
	private EvidenceType evidenceTypeW2;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w3", nullable = false)
	private EvidenceType evidenceTypeW3;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w4", nullable = false)
	private EvidenceType evidenceTypeW4;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w5", nullable = false)
	private EvidenceType evidenceTypeW5;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w6", nullable = false)
	private EvidenceType evidenceTypeW6;

	public Long getId() {
		return id;
	}

	public Person getPersonId() {
		return personId;
	}

	public EvidenceType getEvidenceTypeW1() {
		return evidenceTypeW1;
	}

	public EvidenceType getEvidenceTypeW2() {
		return evidenceTypeW2;
	}

	public EvidenceType getEvidenceTypeW3() {
		return evidenceTypeW3;
	}

	public EvidenceType getEvidenceTypeW4() {
		return evidenceTypeW4;
	}

	public EvidenceType getEvidenceTypeW5() {
		return evidenceTypeW5;
	}

	public EvidenceType getEvidenceTypeW6() {
		return evidenceTypeW6;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	public void setEvidenceTypeW1(EvidenceType evidenceTypeW1) {
		this.evidenceTypeW1 = evidenceTypeW1;
	}

	public void setEvidenceTypeW2(EvidenceType evidenceTypeW2) {
		this.evidenceTypeW2 = evidenceTypeW2;
	}

	public void setEvidenceTypeW3(EvidenceType evidenceTypeW3) {
		this.evidenceTypeW3 = evidenceTypeW3;
	}

	public void setEvidenceTypeW4(EvidenceType evidenceTypeW4) {
		this.evidenceTypeW4 = evidenceTypeW4;
	}

	public void setEvidenceTypeW5(EvidenceType evidenceTypeW5) {
		this.evidenceTypeW5 = evidenceTypeW5;
	}

	public void setEvidenceTypeW6(EvidenceType evidenceTypeW6) {
		this.evidenceTypeW6 = evidenceTypeW6;
	}

}