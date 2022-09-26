package com.ccsw.gtemanager.evidenceerror;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.gtemanager.config.BaseITAbstract;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceErrorDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EvidenceErrorIT extends BaseITAbstract {

    private static final String SERVICE_PATH = "/evidence-error";

    private static final int TOTAL_ERRORS = 3;

    @LocalServerPort
    private int port;

    ParameterizedTypeReference<List<EvidenceErrorDto>> responseTypeList = new ParameterizedTypeReference<List<EvidenceErrorDto>>() {
    };

    /**
     * Obtener centros con GET debería devolver listado de errores
     */
    @Test
    public void getErrorsShouldReturnAllErrors() {

        HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());

        ResponseEntity<List<EvidenceErrorDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, httpEntity, responseTypeList);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TOTAL_ERRORS, response.getBody().size());
    }
}
