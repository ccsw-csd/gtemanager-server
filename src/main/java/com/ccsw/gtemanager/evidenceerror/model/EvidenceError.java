package com.ccsw.gtemanager.evidenceerror.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * EvidenceError: clase para la gestión de errores en evidencias. Contiene
 * atributos para el registro de la evidencia (nombre de persona, código saga,
 * email, periodo, y tipo), además de getters y setters.
 */
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

    @Column(name = "period", nullable = false)
    private String period;

    @Column(name = "status", nullable = false)
    private String status;

    /**
     * Obtener ID de EvidenceError
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
     * Almacenar ID de EvidenceError
     * 
     * @param id ID de EvidenceError (Long)
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
