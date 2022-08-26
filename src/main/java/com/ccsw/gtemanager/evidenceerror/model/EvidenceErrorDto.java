package com.ccsw.gtemanager.evidenceerror.model;

/**
 * EvidenceErrorDto: DTO para datos errores en procesamiento de eidencia.
 * Contiene atributos para almacenar los datos de la línea, además de getters y
 * setters.
 */
public class EvidenceErrorDto {

	private Long id;

	private String name;

	private String saga;

	private String email;

	private String period;

	private String status;

	/**
	 * Constructor vacío para la creación de EvidenceErrorDto
	 */
	public EvidenceErrorDto() {

	}

	/**
	 * Constructor con parámetros de la evidencia a asociar con el error del
	 * registro
	 * 
	 * @param name   Nombre completo de la persona implicada
	 * @param saga   Código saga obtenido del fichero
	 * @param email  Correo electrónico de la persona implicada
	 * @param period Periodo obtenido del fichero
	 * @param status Estado (tipo) de evidencia obtenido del fichero
	 */
	public EvidenceErrorDto(String name, String saga, String email, String period, String status) {
		this.name = name;
		this.saga = saga;
		this.email = email;
		this.period = period;
		this.status = status;
	}

	/**
	 * Obtener ID de EvidenceErrorDto
	 * 
	 * @return ID en formato Long
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Obtener nombre completo de la persona implicada
	 * 
	 * @return nombre en formato String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Obtener código saga registrado en el error
	 * 
	 * @return código en formato String
	 */
	public String getSaga() {
		return saga;
	}

	/**
	 * Obtener correo electrónico de la persona implicada
	 * 
	 * @return correo electrónico en formato String
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Obtener periodo registrado en el error
	 * 
	 * @return periodo en formato String
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * Obtener estado (tipo) de evidencia registrado en el error
	 * 
	 * @return estado en formato String
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Almacenar ID de EvidenceErrorDto
	 * 
	 * @param id ID de EvidenceErrorDto (Long)
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Almacenar nombre de la persona implicada
	 * 
	 * @param name nombre de la persona (String)
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Almacenar código saga de la persona implicada
	 * 
	 * @param saga código de la persona (String)
	 */
	public void setSaga(String saga) {
		this.saga = saga;
	}

	/**
	 * Almacenar email de la persona implicada
	 * 
	 * @param email correo electrónico de la persona (String)
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Almacenar periodo de la evidencia
	 * 
	 * @param period periodo de la evidencia (String)
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * Almacenar estado (tipo) de evidencia
	 * 
	 * @param status estado de evidencia (String)
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
