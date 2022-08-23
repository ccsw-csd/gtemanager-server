package com.ccsw.gtemanager.evidence.model;

import com.ccsw.gtemanager.person.model.PersonDto;

public class EvidenceDto {

	private Long id;

	private PersonDto person;

	private EvidenceTypeDto evidenceTypeW1;
	
	private EvidenceTypeDto evidenceTypeW2;
	
	private EvidenceTypeDto evidenceTypeW3;
	
	private EvidenceTypeDto evidenceTypeW4;
	
	private EvidenceTypeDto evidenceTypeW5;

	private EvidenceTypeDto evidenceTypeW6;

	public Long getId() {
		return id;
	}

	public PersonDto getPerson() {
		return person;
	}

	public EvidenceTypeDto getEvidenceTypeW1() {
		return evidenceTypeW1;
	}

	public EvidenceTypeDto getEvidenceTypeW2() {
		return evidenceTypeW2;
	}

	public EvidenceTypeDto getEvidenceTypeW3() {
		return evidenceTypeW3;
	}

	public EvidenceTypeDto getEvidenceTypeW4() {
		return evidenceTypeW4;
	}

	public EvidenceTypeDto getEvidenceTypeW5() {
		return evidenceTypeW5;
	}

	public EvidenceTypeDto getEvidenceTypeW6() {
		return evidenceTypeW6;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPerson(PersonDto person) {
		this.person = person;
	}

	public void setEvidenceTypeW1(EvidenceTypeDto evidenceW1) {
		this.evidenceTypeW1 = evidenceW1;
	}

	public void setEvidenceTypeW2(EvidenceTypeDto evidenceW2) {
		this.evidenceTypeW2 = evidenceW2;
	}

	public void setEvidenceTypeW3(EvidenceTypeDto evidenceW3) {
		this.evidenceTypeW3 = evidenceW3;
	}

	public void setEvidenceTypeW4(EvidenceTypeDto evidenceW4) {
		this.evidenceTypeW4 = evidenceW4;
	}

	public void setEvidenceTypeW5(EvidenceTypeDto evidenceW5) {
		this.evidenceTypeW5 = evidenceW5;
	}

	public void setEvidenceTypeW6(EvidenceTypeDto evidenceW6) {
		this.evidenceTypeW6 = evidenceW6;
	}

}