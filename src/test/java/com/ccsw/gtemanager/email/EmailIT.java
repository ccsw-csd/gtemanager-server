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
 * TODO DOCS
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EmailIT extends BaseITAbstract {

    private static final String LOCALHOST = "http://localhost:";
    private static final String SERVICE_PATH = "/email/send";

    private static final String TEST_STRING = "test";

    private static final String CLOSING_DATE_PARAM = "closingDate";
    private static final String CENTER_ID_PARAM = "centerId";

    private static final LocalDate EXISTING_CLOSING_DATE = LocalDate.parse("2022-09-09");
    private static final Long EXISTING_CENTER_ID = 3L;
    private static final LocalDate NONEXISTING_CLOSING_DATE = LocalDate.parse("2022-19-09");
    private static final Long NONEXISTING_CENTER_ID = 0L;

    @LocalServerPort
    private int port;

//    @Autowired
//    private EvidenceService evidenceService;

    /**
     * Obtener URL con parámetros concatenados.
     * 
     * @return URL con parámetros
     */
    private String getUrlWithParams() {
        return UriComponentsBuilder.fromHttpUrl(LOCALHOST + port + SERVICE_PATH)
                .queryParam(CLOSING_DATE_PARAM, "{" + CLOSING_DATE_PARAM + "}")
                .queryParam(CENTER_ID_PARAM, "{" + CENTER_ID_PARAM + "}").encode().toUriString();
    }

    /**
     * TODO DOCS
     *
     */
    @Test
    public void requestWithValidDateAndCenterShouldSendEmails() {
        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_PARAM, EXISTING_CLOSING_DATE);
        params.put(CENTER_ID_PARAM, EXISTING_CENTER_ID);

        ResponseEntity<?> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, null, String.class,
                params);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
    }

//    @Test
//    public void requestWithValidDataAndSendErrorsShouldSendEmailsAndReturnMessage() {
//        Evidence evidence = evidenceService.getEvidenceForPerson(new Person("1"));
//// TODO modificar email para que devuelva petición incorrecta (algunos emails no se han podido enviar)
//        Map<String, Object> params = new LinkedHashMap<>();
//
//        params.put(CLOSING_DATE_PARAM, EXISTING_CLOSING_DATE);
//        params.put(CENTER_ID_PARAM, EXISTING_CENTER_ID);
//
//        ResponseEntity<?> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, null, String.class,
//                params);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotEquals(null, response.getBody());
//    }

    /**
     * TODO DOCS
     *
     */
    @Test
    public void requestWithInvalidDataShouldReturnError() {
        String test = TEST_STRING;

        ResponseEntity<?> response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, new HttpEntity<>(test),
                String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Map<String, Object> params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_PARAM, NONEXISTING_CLOSING_DATE);
        params.put(CENTER_ID_PARAM, EXISTING_CENTER_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, null, String.class, params);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        params.clear();
        params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_PARAM, EXISTING_CLOSING_DATE);
        params.put(CENTER_ID_PARAM, NONEXISTING_CENTER_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, null, String.class, params);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        params.clear();
        params = new LinkedHashMap<>();

        params.put(CLOSING_DATE_PARAM, NONEXISTING_CLOSING_DATE);
        params.put(CENTER_ID_PARAM, NONEXISTING_CENTER_ID);

        response = restTemplate.exchange(getUrlWithParams(), HttpMethod.POST, null, String.class, params);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
