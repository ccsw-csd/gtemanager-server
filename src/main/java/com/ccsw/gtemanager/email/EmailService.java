package com.ccsw.gtemanager.email;

import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.email.model.ReminderDto;

/**
 * EmailService: servicio de procesamiento y envío de correos electrónicos.
 */
public interface EmailService {

    /**
     * Enviar mensajes de recordatorio a las personas asociadas a un centro
     * determinado, anotando la fecha de cierre.
     * 
     * @param reminder Objeto ReminderDto con fecha de cierre e ID de centro
     * @throws ResponseStatusException Hay errores a la hora de procesar los
     *                                 mensajes
     */
    boolean sendReminderEmails(ReminderDto reminder) throws ResponseStatusException;

}
