package com.ccsw.gtemanager.evidence.model;

import com.ccsw.gtemanager.person.model.PersonDto;

public class EvidenceDto {

	private Long id;

	private PersonDto personId;

	private EvidenceTypeDto evidenceTypeW1, evidenceTypeW2, evidenceTypeW3, evidenceTypeW4, evidenceTypeW5,
			evidenceTypeW6;

	public Long getId() {
		return id;
	}

	public PersonDto getPersonId() {
		return personId;
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

	public void setPersonId(PersonDto personId) {
		this.personId = personId;
	}

	public void setEvidenceTypeW1(EvidenceTypeDto evidenceTypeW1) {
		this.evidenceTypeW1 = evidenceTypeW1;
	}

	public void setEvidenceTypeW2(EvidenceTypeDto evidenceTypeW2) {
		this.evidenceTypeW2 = evidenceTypeW2;
	}

	public void setEvidenceTypeW3(EvidenceTypeDto evidenceTypeW3) {
		this.evidenceTypeW3 = evidenceTypeW3;
	}

	public void setEvidenceTypeW4(EvidenceTypeDto evidenceTypeW4) {
		this.evidenceTypeW4 = evidenceTypeW4;
	}

	public void setEvidenceTypeW5(EvidenceTypeDto evidenceTypeW5) {
		this.evidenceTypeW5 = evidenceTypeW5;
	}

	public void setEvidenceTypeW6(EvidenceTypeDto evidenceTypeW6) {
		this.evidenceTypeW6 = evidenceTypeW6;
	}

}