package com.ccsw.gtemanager.person;

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
import com.ccsw.gtemanager.person.model.PersonDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class PersonIT extends BaseITAbstract {

    public static final String SERVICE_PATH = "/person/";

    ParameterizedTypeReference<List<PersonDto>> responseTypeList = new ParameterizedTypeReference<List<PersonDto>>() {
    };

    @Test
    public void findPersonsByFilter() {

        String filter = "Armen";

        HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());

        ResponseEntity<List<PersonDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + filter,
                HttpMethod.GET, httpEntity, responseTypeList);

        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals(response.getBody().get(0).getName(), filter);

    }

}
