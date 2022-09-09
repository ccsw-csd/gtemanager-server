package com.ccsw.gtemanager.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.email.model.ReminderDto;

/**
 * EmailController: Controlador REST para interacción con datos. Contiene
 * métodos de acceso a servicio de datos, asociados a Requests HTTP.
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
     * @param reminder Objeto ReminderDto con fecha de cierre e ID de centro
     * @return NULL si se ha procesado correctamente, mensaje si se ha procesado
     *         correctamente, pero con errores.
     */
    @RequestMapping(path = "/sendReminders", method = RequestMethod.POST)
    public ResponseEntity<String> sendReminders(@RequestBody ReminderDto reminder) {
        if (emailService.sendReminderEmails(reminder))
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .body("\"Se han enviado mensajes, pero se han producido algunos fallos.\"");
    }
}
