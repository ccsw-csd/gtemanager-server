package com.ccsw.gtemanager.evidence.model;

public class EvidenceTypeDto {

	private Long id;

	private String code, name;

	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

}
