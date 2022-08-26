package com.ccsw.gtemanager.evidenceview.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;

@Entity
@Table(name = "v_evidence_with_comment")
@Immutable
@NamedEntityGraph(
	name = "evidence-view-graph",
	attributeNodes = {
		@NamedAttributeNode("comment"),
		@NamedAttributeNode("evidenteTypeW1"),
		@NamedAttributeNode("evidenteTypeW2"),
		@NamedAttributeNode("evidenteTypeW3"),
		@NamedAttributeNode("evidenteTypeW4"),
		@NamedAttributeNode("evidenteTypeW5"),
		@NamedAttributeNode("evidenteTypeW6")
	},
	subgraphs = {
		@NamedSubgraph(
			name = "id-subgraph",
			attributeNodes = {
				@NamedAttributeNode("person")
			}
		)
	}
)
public class EvidenceView {

	@EmbeddedId
	private EvidenceViewId evidence;

	@ManyToOne
	@JoinColumn(name = "comment_id", nullable = true)
	private EvidenceComment comment;
	
	@ManyToOne
	@JoinColumn(name = "evidente_type_w1", nullable = true)
	private EvidenceType evidenteTypeW1;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w2", nullable = true)
	private EvidenceType evidenteTypeW2;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w3", nullable = true)
	private EvidenceType evidenteTypeW3;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w4", nullable = true)
	private EvidenceType evidenteTypeW4;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w5", nullable = true)
	private EvidenceType evidenteTypeW5;

	@ManyToOne
	@JoinColumn(name = "evidente_type_w6", nullable = true)
	private EvidenceType evidenteTypeW6;
	
	public EvidenceViewId getEvidence() {
		return evidence;
	}
	
	public EvidenceComment getComment() {
		return comment;
	}

	public EvidenceType getEvidenteTypeW1() {
		return evidenteTypeW1;
	}

	public EvidenceType getEvidenteTypeW2() {
		return evidenteTypeW2;
	}

	public EvidenceType getEvidenteTypeW3() {
		return evidenteTypeW3;
	}

	public EvidenceType getEvidenteTypeW4() {
		return evidenteTypeW4;
	}

	public EvidenceType getEvidenteTypeW5() {
		return evidenteTypeW5;
	}

	public EvidenceType getEvidenteTypeW6() {
		return evidenteTypeW6;
	}
}