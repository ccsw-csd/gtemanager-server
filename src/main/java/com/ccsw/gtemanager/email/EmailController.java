package com.ccsw.gtemanager.email;

import java.io.IOException;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

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
     * TODO DOCS
     * 
     * @return
     * @throws MessagingException
     * @throws IOException
     * @throws AddressException
     */
    @RequestMapping(path = "/send", method = RequestMethod.POST)
    public ResponseEntity<String> sendEmails(
            @RequestParam(name = "closingDate", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate closingDate,
            @RequestParam(name = "centerId", required = true) Long centerId) {
        if (emailService.sendEmails(closingDate, centerId))
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK)
                    .body("\"Se han enviado mensajes, pero se han producido algunos fallos.\"");
    }
}
