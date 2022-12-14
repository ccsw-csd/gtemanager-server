package com.ccsw.gtemanager.center;

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

import com.ccsw.gtemanager.center.model.CenterDto;
import com.ccsw.gtemanager.config.BaseITAbstract;

/**
 * CenterIT: colección de tests integrados que prueban funcionalidad del
 * programa y API.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CenterIT extends BaseITAbstract {

    private static final String LOCALHOST = "http://localhost:";
    private static final String SERVICE_PATH = "/center";

    private static final int TOTAL_CENTERS = 3;

    @LocalServerPort
    private int port;

    ParameterizedTypeReference<List<CenterDto>> responseTypeList = new ParameterizedTypeReference<List<CenterDto>>() {
    };

    /**
     * Obtener centros con GET debería devolver listado de centros.
     */
    @Test
    public void getCentersShouldReturnAllCenters() {
        ResponseEntity<List<CenterDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, new HttpEntity<>(getHeaders()), responseTypeList);

        assertNotNull(response);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TOTAL_CENTERS, response.getBody().size());
    }
}
