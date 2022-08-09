package com.ccsw.gtemanager.evidence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "evidence_error")
public class EvidenceError {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "saga", nullable = false)
	private String saga;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "status", nullable = false)
	private String status;

	@Column(name = "period", nullable = false)
	private String period;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSaga() {
		return saga;
	}

	public String getEmail() {
		return email;
	}

	public String getStatus() {
		return status;
	}

	public String getPeriod() {
		return period;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSaga(String saga) {
		this.saga = saga;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

}
