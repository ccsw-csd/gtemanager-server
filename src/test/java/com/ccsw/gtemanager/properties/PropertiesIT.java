package com.ccsw.gtemanager.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.gtemanager.config.BaseITAbstract;
import com.ccsw.gtemanager.properties.model.PropertiesDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PropertiesIT extends BaseITAbstract {

    private static final String SERVICE_PATH = "/properties";

    public static final Integer TOTAL_PROPERTIES = 9;

    ParameterizedTypeReference<List<PropertiesDto>> responseTypeProperties = new ParameterizedTypeReference<List<PropertiesDto>>() {
    };

    @Test
    public void getPropertiesShouldReturnAll() {

        HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());

        ResponseEntity<List<PropertiesDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, httpEntity, responseTypeProperties);

        assertNotNull(response.getBody());
        assertEquals(TOTAL_PROPERTIES, response.getBody().size());
    }
}
