package com.ccsw.gtemanager.evidenceview.model;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceCommentDto;
import com.ccsw.gtemanager.evidencetype.model.EvidenceTypeDto;
import com.ccsw.gtemanager.person.model.PersonDto;

public class EvidenceViewDto {
	
	private PersonDto person;
	
	private EvidenceCommentDto comment;
	
	private EvidenceTypeDto evidenceTypeW1; 
	
	private EvidenceTypeDto evidenceTypeW2;
	
	private EvidenceTypeDto evidenceTypeW3; 
	
	private EvidenceTypeDto evidenceTypeW4; 
	
	private EvidenceTypeDto evidenceTypeW5; 
	
	private EvidenceTypeDto evidenceTypeW6;

	public PersonDto getPerson() {
		return person;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public EvidenceCommentDto getComment() {
		return comment;
	}

	public void setComment(EvidenceCommentDto comment) {
		this.comment = comment;
	}

	public EvidenceTypeDto getEvidenceTypeW1() {
		return evidenceTypeW1;
	}

	public void setEvidenceTypeW1(EvidenceTypeDto evidenceTypeW1) {
		this.evidenceTypeW1 = evidenceTypeW1;
	}

	public EvidenceTypeDto getEvidenceTypeW2() {
		return evidenceTypeW2;
	}

	public void setEvidenceTypeW2(EvidenceTypeDto evidenceTypeW2) {
		this.evidenceTypeW2 = evidenceTypeW2;
	}

	public EvidenceTypeDto getEvidenceTypeW3() {
		return evidenceTypeW3;
	}

	public void setEvidenceTypeW3(EvidenceTypeDto evidenceTypeW3) {
		this.evidenceTypeW3 = evidenceTypeW3;
	}

	public EvidenceTypeDto getEvidenceTypeW4() {
		return evidenceTypeW4;
	}

	public void setEvidenceTypeW4(EvidenceTypeDto evidenceTypeW4) {
		this.evidenceTypeW4 = evidenceTypeW4;
	}

	public EvidenceTypeDto getEvidenceTypeW5() {
		return evidenceTypeW5;
	}

	public void setEvidenceTypeW5(EvidenceTypeDto evidenceTypeW5) {
		this.evidenceTypeW5 = evidenceTypeW5;
	}

	public EvidenceTypeDto getEvidenceTypeW6() {
		return evidenceTypeW6;
	}

	public void setEvidenceTypeW6(EvidenceTypeDto evidenceTypeW6) {
		this.evidenceTypeW6 = evidenceTypeW6;
	}
}
