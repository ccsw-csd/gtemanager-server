package com.ccsw.gtemanager.email;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * TODO DOCS
 *
 */
@RequestMapping(value = "/email")
@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    /**
     * POST: procesar y enviar correos electrónicos de recordatorio a los empleados
     * en el área geográfica seleccionada.
     * 
     * @return
     * @throws MessagingException
     * @throws IOException
     * @throws AddressException
     */
    @RequestMapping(path = "/sendReminders", method = RequestMethod.POST)
    public ResponseEntity<String> sendReminders(
            @RequestParam(name = "closingDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate closingDate,
            @RequestParam(name = "centerId", required = true) Long centerId) {
        if (emailService.sendReminderEmails(closingDate, centerId))
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .body("\"Se han enviado mensajes, pero se han producido algunos fallos.\"");
    }
}
