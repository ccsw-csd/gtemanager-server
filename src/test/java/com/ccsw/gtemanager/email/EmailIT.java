package com.ccsw.gtemanager.email;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import com.ccsw.gtemanager.config.BaseITAbstract;

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

    private static final String CLOSING_DATE_VARIABLE = "closingDate";
    private static final String CENTER_ID_VARIABLE = "centerId";

    private static final LocalDate EXISTING_CLOSING_DATE = LocalDate.parse("2022-12-09");
    private static final LocalDate NONEXISTING_CLOSING_DATE = LocalDate.parse("2020-12-09");
    private static final Long EXISTING_CENTER_ID = 3L;
    private static final Long NONEXISTING_CENTER_ID = 0L;

    @LocalServerPort
    private int port;

    /**
     * Obtener URL con parámetros concatenados.
     * 
     * @return URL con parámetros
     */
    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(CLOSING_DATE_VARIABLE, "{" + CLOSING_DATE_VARIABLE + "}")
                .queryParam(CENTER_ID_VARIABLE, "{" + CENTER_ID_VARIABLE + "}").encode().toUriString();
    }

    /**
     * Petición POST con fecha de cierre y centro existentes debería envíar emails.
     */
    @Test
    public void requestWithValidDateAndCenterShouldSendEmails() {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_VARIABLE, EXISTING_CLOSING_DATE);
        params.put(CENTER_ID_VARIABLE, EXISTING_CENTER_ID);

        ResponseEntity<?> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(getHeaders()), String.class, params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

    /**
     * Petición POST con fecha de cierre y/o centro incorrectos debería devolver
     * error.
     */
    @Test
    public void requestWithInvalidDataShouldReturnError() {

        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_VARIABLE, NONEXISTING_CLOSING_DATE);
        params.put(CENTER_ID_VARIABLE, EXISTING_CENTER_ID);

        ResponseEntity<?> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST,
                new HttpEntity<>(getHeaders()), String.class, params);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        params.clear();
        params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_VARIABLE, EXISTING_CLOSING_DATE);
        params.put(CENTER_ID_VARIABLE, NONEXISTING_CENTER_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(getHeaders()),
                String.class, params);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        params.clear();
        params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_VARIABLE, NONEXISTING_CLOSING_DATE);
        params.put(CENTER_ID_VARIABLE, NONEXISTING_CENTER_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(getHeaders()),
                String.class, params);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
