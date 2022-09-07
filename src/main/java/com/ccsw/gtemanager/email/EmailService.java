package com.ccsw.gtemanager.email;

import java.time.LocalDate;

import org.springframework.web.server.ResponseStatusException;

/**
 * EmailService: servicio de procesamiento y envío de correos electrónicos.
 */
public interface EmailService {

    /**
     * TODO DOCS
     * 
     */
    boolean sendReminderEmails(LocalDate closingDate, Long centerId) throws ResponseStatusException;

}
