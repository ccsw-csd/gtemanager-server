package com.ccsw.gtemanager.evidence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EvidenceType: clase para la gestión de tipos de evidencia. Contiene atributos
 * para el código y el nombre, además de getters y setters.
 */
@Entity
@Table(name = "evidence_type")
public class EvidenceType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "name")
	private String name;

	/**
	 * Obtener ID de EvidenceType
	 * 
	 * @return ID en formato Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtener código de EvidenceType
	 * 
	 * @return código en formato String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Obtener nombre de EvidenceType
	 * 
	 * @return nombre en formato String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Almacenar ID de EvidenceType
	 * 
	 * @param id ID de tipo de evidencia (Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Almacenar código de EvidenceType
	 * 
	 * @param code código de tipo de evidencia (String)
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Almacenar nombre de EvidenceType
	 * 
	 * @param name nombre de tipo de evidencia (String)
	 */
	public void setName(String name) {
		this.name = name;
	}

}
