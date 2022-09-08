package com.ccsw.gtemanager.email;

import java.time.LocalDate;

import org.springframework.web.server.ResponseStatusException;

/**
 * EmailService: servicio de procesamiento y envío de correos electrónicos.
 */
public interface EmailService {

    /**
     * Enviar mensajes de recordatorio a las personas asociadas a un centro
     * determinado, anotando la fecha de cierre.
     * 
     * @param closingDate Fecha de cierre de periodo
     * @param centerId    ID del centro asociado
     * @throws ResponseStatusException Hay errores a la hora de procesar los
     *                                 mensajes
     */
    boolean sendReminderEmails(LocalDate closingDate, Long centerId) throws ResponseStatusException;

}
