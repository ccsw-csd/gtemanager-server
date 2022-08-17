package com.ccsw.gtemanager.properties.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "properties")
public class Properties {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "key", nullable = false)
	private String key;

	@Column(name = "value")
	private String value;

	public Properties(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
