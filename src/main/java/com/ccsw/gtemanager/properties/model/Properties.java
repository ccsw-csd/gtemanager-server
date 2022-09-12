package com.ccsw.gtemanager.properties.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Properties: clase para la gestión de datos de propiedades. Contiene atributos
 * para clave y valor, además de getters y setters.
 */
@Entity
@Table(name = "properties")
public class Properties {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "key", nullable = false)
	private String key;
	
	@Column(name = "value", nullable = false)
	private String value;

    /**
     * Constructor vacío para la creación de Properties
     */
    public Properties() {

    }

    /**
     * Constructor con parámetros para almacenar propiedad y valor.
     * 
     * @param key   Propiedad a almacenar
     * @param value Valor asignado a la propiedad
     */
    public Properties(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Obtener ID de Properties
     * 
     * @return ID en formato Long
     */
	public Long getId() {
		return id;
	}

    /**
     * Obtener clave de propiedad almacenada
     * 
     * @return clave en formato String
     */
	public String getKey() {
		return key;
	}

    /**
     * Obtener valor de propiedad almacenada
     * 
     * @return valor en formato String
     */
	public String getValue() {
		return value;
	}

    /**
     * Almacenar ID de Properties
     * 
     * @param id ID de Properties (Long)
     */
	public void setId(Long id) {
		this.id = id;
	}

    /**
     * Almacenar clave de la propiedad
     * 
     * @param key clave (String)
     */
	public void setKey(String key) {
		this.key = key;
	}

    /**
     * Almacenar valor de la propiedad
     * 
     * @param value valor (String)
     */
	public void setValue(String value) {
		this.value = value;
	}
}
