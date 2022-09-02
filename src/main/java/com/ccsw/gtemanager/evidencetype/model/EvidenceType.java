package com.ccsw.gtemanager.evidencetype.model;

import java.util.Objects;

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
     * Constructor vacío para la creación de EvidenceType
     */
    public EvidenceType() {

    }

    /**
     * Constructor con parámetro para asociar código a EvidenceType
     * 
     * @param code Código de tipo de evidencia
     */
    public EvidenceType(String code) {
        this.code = code;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EvidenceType other = (EvidenceType) obj;
        return Objects.equals(code, other.code);
    }

}
