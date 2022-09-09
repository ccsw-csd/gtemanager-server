package com.ccsw.gtemanager.email.model;

import java.time.LocalDate;

/**
 * ReminderDto: DTO para recepción de datos para envío de mensajes de
 * recordatorio. Contiene atributos para fecha de cierre e ID de centro, además
 * de getters y setters.
 */
public class ReminderDto {

    private LocalDate closingDate;
    private Long centerId;

    /**
     * Obtener fecha de cierre almacenada
     * 
     * @return Fecha de cierre en formato LocalDate
     */
    public LocalDate getClosingDate() {
        return closingDate;
    }

    /**
     * Obtener ID del centro almacenada
     * 
     * @return ID de Center en formato Long
     */
    public Long getCenterId() {
        return centerId;
    }

    /**
     * Almacenar fecha de cierre
     * 
     * @param closingDate Fecha de cierre (LocalDate)
     */
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }

    /**
     * Almacenar ID de Center
     * 
     * @param centerId ID de centro (Long)
     */
    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

}
