package com.ccsw.gtemanager.evidenceview.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;

@Embeddable
public class EvidenceViewPK implements Serializable {

	@Column(nullable = false)
	private Long evidenceId;
	
	@ManyToOne
	@JoinColumn(name = "comment_id", nullable = true)
	EvidenceComment comment;

	public Long getEvidence_id() {
		return evidenceId;
	}

	public void setEvidence_id(Long evidence_id) {
		this.evidenceId = evidence_id;
	}

	public EvidenceComment getComment() {
		return comment;
	}

	public void setComment(EvidenceComment comment) {
		this.comment = comment;
	}
	
	
}
