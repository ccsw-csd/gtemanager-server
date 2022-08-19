package com.ccsw.gtemanager.person.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PersonSagaTranscode: clase de apoyo para resolución de códigos saga de Person
 * en base de datos. Contiene atributos para la persona y código de saga, además
 * de getters y setters.
 */
@Entity
@Table(name = "person_saga_transcode")
public class PersonSagaTranscode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false)
	private Person personId;

	@Column(name = "saga", nullable = false)
	private String saga;

	/**
	 * Obtener ID del objeto
	 * 
	 * @return ID en formato Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtener Person asociada
	 * 
	 * @return persona (Person)
	 */
	public Person getPersonId() {
		return personId;
	}

	/**
	 * Obtener código saga resuelto
	 * 
	 * @return código saga en formato String
	 */
	public String getSaga() {
		return saga;
	}

	/**
	 * Almacenar ID de objeto
	 * 
	 * @param id ID de objeto (Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Almacenar persona a asociar código
	 * 
	 * @param personId persona (Person)
	 */
	public void setPersonId(Person personId) {
		this.personId = personId;
	}

	/**
	 * Almacenar código saga a asociar
	 * 
	 * @param saga código saga (String)
	 */
	public void setSaga(String saga) {
		this.saga = saga;
	}

}
