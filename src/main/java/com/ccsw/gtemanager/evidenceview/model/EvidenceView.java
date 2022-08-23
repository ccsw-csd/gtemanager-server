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

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "evidente_type_w1", nullable = false)
	private EvidenceType evidenceW1;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w2", nullable = false)
	private EvidenceType evidenceW2;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w3", nullable = false)
	private EvidenceType evidenceW3;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w4", nullable = false)
	private EvidenceType evidenceW4;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w5", nullable = false)
	private EvidenceType evidenceW5;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w6", nullable = false)
	private EvidenceType evidenceW6;

	public EvidenceViewPK getEvidencePK() {
		return evidence;
	}
	
	public Person getPerson() {
		return person;
	}

	public EvidenceType getEvidenceW1() {
		return evidenceW1;
	}

	public EvidenceType getEvidenceW2() {
		return evidenceW2;
	}

	public EvidenceType getEvidenceW3() {
		return evidenceW3;
	}

	public EvidenceType getEvidenceW4() {
		return evidenceW4;
	}

	public EvidenceType getEvidenceW5() {
		return evidenceW5;
	}

	public EvidenceType getEvidenceW6() {
		return evidenceW6;
	}
}