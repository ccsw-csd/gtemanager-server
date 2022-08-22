package com.ccsw.gtemanager.evidenceview.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidenceview.model.EvidenceViewPK;
import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "v_evidence_with_comment")
@Immutable
public class EvidenceView {

	@EmbeddedId
	private EvidenceViewPK evidence;
	/*
	@ManyToOne
	@JoinColumn(name = "id")
	private Evidence evidence;*/

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;
	/*
	@ManyToOne
	@JoinColumn(name = "comment_id", nullable = true)
	private EvidenceComment comment;*/
	
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

	/*public Evidence getEvidence() {
		return evidence;
	}*/

	public Person getPerson() {
		return person;
	}

	/*public EvidenceComment getComment() {
		return comment;
	}*/

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
}