package com.ccsw.gtemanager.center;

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

import com.ccsw.gtemanager.center.model.CenterDto;
import com.ccsw.gtemanager.config.BaseITAbstract;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class CenterIT extends BaseITAbstract {

	public static final String SERVICE_PATH = "/center/";
	
	public static final Integer TOTAL_CENTER = 3;
	
	ParameterizedTypeReference<List<CenterDto>> responseTypeCenter = 
			new ParameterizedTypeReference<List<CenterDto>>() {};
			
	@Test
	public void findAllShouldReturnCenterList() {
		
		HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());
		
		ResponseEntity<List<CenterDto>> response = restTemplate.exchange(
				LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, httpEntity, responseTypeCenter);
		
		assertNotNull(response.getBody());
		assertEquals(TOTAL_CENTER, response.getBody().size());
	}
}
