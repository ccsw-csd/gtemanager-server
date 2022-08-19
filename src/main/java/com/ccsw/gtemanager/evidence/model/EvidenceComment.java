package com.ccsw.gtemanager.evidence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.person.model.Person;

/**
 * EvidenceComment: clase para la gestión de comentarios de evidencias. Contiene
 * atributos para almacenar la persona implicada y el comentario, además de
 * getters y setters.
 */
@Entity
@Table(name = "evidence_comment")
public class EvidenceComment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person personId;

	@Column(name = "comment")
	private String comment;

	/**
	 * Obtener ID de EvidenceComment
	 * 
	 * @return ID en formato Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtener Person implicada en EvidenceComment
	 * 
	 * @return persona (Person)
	 */
	public Person getPersonId() {
		return personId;
	}

	/**
	 * Obtener comentario almacenado en EvidenceComment
	 * 
	 * @return comentario en formato String
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Almacenar ID de EvidenceComment
	 * 
	 * @param id ID de EvidenceComment (Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Almacenar Person implicada en EvidenceComment
	 * 
	 * @param personId persona (Person)
	 */
	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	/**
	 * Almacenar comentario en EvidenceComment
	 * 
	 * @param comment comentario (String)
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

}