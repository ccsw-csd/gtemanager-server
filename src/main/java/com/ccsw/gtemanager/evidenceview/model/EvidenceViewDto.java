package com.ccsw.gtemanager.evidenceview.model;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceCommentDto;
import com.ccsw.gtemanager.evidencetype.model.EvidenceTypeDto;
import com.ccsw.gtemanager.person.model.PersonDto;

public class EvidenceViewDto {
	
	private EvidenceViewIdDto evidence;
	
	private EvidenceCommentDto comment;
	
	private EvidenceTypeDto evidenteTypeW1; 
	
	private EvidenceTypeDto evidenteTypeW2;
	
	private EvidenceTypeDto evidenteTypeW3; 
	
	private EvidenceTypeDto evidenteTypeW4; 
	
	private EvidenceTypeDto evidenteTypeW5; 
	
	private EvidenceTypeDto evidenteTypeW6;

	public EvidenceViewIdDto getEvidence() {
		return evidence;
	}

	public void setEvidence(EvidenceViewIdDto evidence) {
		this.evidence = evidence;
	}

	public EvidenceCommentDto getComment() {
		return comment;
	}

	public void setComment(EvidenceCommentDto comment) {
		this.comment = comment;
	}

	public EvidenceTypeDto getEvidenteTypeW1() {
		return evidenteTypeW1;
	}

	public void setEvidenteTypeW1(EvidenceTypeDto evidenteTypeW1) {
		this.evidenteTypeW1 = evidenteTypeW1;
	}

	public EvidenceTypeDto getEvidenteTypeW2() {
		return evidenteTypeW2;
	}

	public void setEvidenteW2(EvidenceTypeDto evidenteTypeW2) {
		this.evidenteTypeW2 = evidenteTypeW2;
	}

	public EvidenceTypeDto getEvidenteTypeW3() {
		return evidenteTypeW3;
	}

	public void setEvidenteTypeW3(EvidenceTypeDto evidenteTypeW3) {
		this.evidenteTypeW3 = evidenteTypeW3;
	}

	public EvidenceTypeDto getEvidenteTypeW4() {
		return evidenteTypeW4;
	}

	public void setEvidenteTypeW4(EvidenceTypeDto evidenteTypeW4) {
		this.evidenteTypeW4 = evidenteTypeW4;
	}

	public EvidenceTypeDto getEvidenteTypeW5() {
		return evidenteTypeW5;
	}

	public void setEvidenteW5(EvidenceTypeDto evidenteTypeW5) {
		this.evidenteTypeW5 = evidenteTypeW5;
	}

	public EvidenceTypeDto getEvidenteTypeW6() {
		return evidenteTypeW6;
	}

	public void setEvidenteW6(EvidenceTypeDto evidenteTypeW6) {
		this.evidenteTypeW6 = evidenteTypeW6;
	}
}
