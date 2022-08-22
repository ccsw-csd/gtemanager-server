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
	private Long evidence_id;
	
	@ManyToOne
	@JoinColumn(name = "comment_id", nullable = true)
	EvidenceComment comment;
}
