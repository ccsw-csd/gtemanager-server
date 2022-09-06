package com.ccsw.gtemanager.email;

import java.io.IOException;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

/**
 * TODO DOCS
 * 
 *
 */
public interface EmailService {

    /**
     * TODO DOCS
     * 
     * @throws IOException
     * @throws MessagingException
     * @throws AddressException
     */
    boolean sendEmails(LocalDate closingDate, Long centerId);

}
