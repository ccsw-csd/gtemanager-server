package com.ccsw.gtemanager.email;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.gtemanager.config.BaseITAbstract;
import com.ccsw.gtemanager.email.model.ReminderDto;

/**
 * EmailIT: colección de tests integrados que prueban funcionalidad del programa
 * y API.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmailIT extends BaseITAbstract {

    private static final String LOCALHOST = "http://localhost:";
    private static final String SERVICE_PATH = "/email/sendReminders";

    private static final String TEST_STRING = "test";

    private static final LocalDate EXISTING_CLOSING_DATE = LocalDate.parse("2070-12-09");
    private static final LocalDate NONEXISTING_CLOSING_DATE = LocalDate.parse("2020-12-09");
    private static final Long EXISTING_CENTER_ID = 3L;
    private static final Long NONEXISTING_CENTER_ID = 0L;

    @LocalServerPort
    private int port;

    /**
     * Petición POST con fecha de cierre y centro existentes debería envíar emails.
     */
    @Test
    public void requestWithValidDateAndCenterShouldSendEmails() {
        ReminderDto reminder = new ReminderDto();

        reminder.setClosingDate(EXISTING_CLOSING_DATE);
        reminder.setCenterId(EXISTING_CENTER_ID);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(reminder, getHeaders()), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    /**
     * Petición POST con fecha de cierre y/o centro incorrectos debería devolver
     * error.
     */
    @Test
    public void requestWithInvalidDataShouldReturnError() {
        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(TEST_STRING, getHeaders()), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ReminderDto reminder = new ReminderDto();

        reminder.setClosingDate(NONEXISTING_CLOSING_DATE);
        reminder.setCenterId(EXISTING_CENTER_ID);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(reminder, getHeaders()), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        reminder.setClosingDate(EXISTING_CLOSING_DATE);
        reminder.setCenterId(NONEXISTING_CENTER_ID);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(reminder, getHeaders()), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        reminder.setClosingDate(NONEXISTING_CLOSING_DATE);
        reminder.setCenterId(NONEXISTING_CENTER_ID);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(reminder, getHeaders()), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
