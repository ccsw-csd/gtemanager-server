package com.ccsw.gtemanager.evidenceview.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import com.ccsw.gtemanager.evidencecomment.model.Comment;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "v_evidence_with_comment")
@Immutable
@NamedEntityGraph(
	name = "evidence-view-graph",
	attributeNodes = {
		@NamedAttributeNode("person"),
		@NamedAttributeNode("comment"),
		@NamedAttributeNode("evidenceTypeW1"),
		@NamedAttributeNode("evidenceTypeW2"),
		@NamedAttributeNode("evidenceTypeW3"),
		@NamedAttributeNode("evidenceTypeW4"),
		@NamedAttributeNode("evidenceTypeW5"),
		@NamedAttributeNode("evidenceTypeW6")
	}
)
public class EvidenceView {
	
	@Id
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "comment_id", nullable = true)
	private Comment comment;
	
	@ManyToOne
	@JoinColumn(name = "evidente_type_w1", nullable = true)
	private EvidenceType evidenceTypeW1;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w2", nullable = true)
	private EvidenceType evidenceTypeW2;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w3", nullable = true)
	private EvidenceType evidenceTypeW3;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w4", nullable = true)
	private EvidenceType evidenceTypeW4;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w5", nullable = true)
	private EvidenceType evidenceTypeW5;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w6", nullable = true)
	private EvidenceType evidenceTypeW6;
	
	public Person getPerson() {
		return person;
	}
	
	public Comment getComment() {
		return comment;
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
}