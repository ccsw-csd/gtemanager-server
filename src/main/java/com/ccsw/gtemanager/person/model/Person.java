package com.ccsw.gtemanager.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.center.model.Center;

@Entity
@Table(name = "person")
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "saga", nullable = false)
	private String saga;

	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "lastname", nullable = false)
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "center")
	private Center center;

	@Column(name = "businesscode")
	private String businessCode;

	@Column(name = "grade")
	private String grade;

	@Column(name = "active", nullable = false)
	private boolean active;

	public Long getId() {
		return id;
	}

	public String getSaga() {
		return saga;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public Center getCenter() {
		return center;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public String getGrade() {
		return grade;
	}

	public boolean isActive() {
		return active;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSaga(String saga) {
		this.saga = saga;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCenter(Center center) {
		this.center = center;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
