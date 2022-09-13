package com.ccsw.gtemanager.evidenceview;

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
import com.ccsw.gtemanager.evidenceview.model.EvidenceViewDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EvidenceViewIT extends BaseITAbstract {

	private static final String SERVICE_PATH = "/evidence-view";
	
	public static final Integer TOTAL_EVIDENCE = 6;
	
	ParameterizedTypeReference<List<EvidenceViewDto>> responseTypeEvidenceView = new ParameterizedTypeReference<List<EvidenceViewDto>>() {};
	
	@Test
	public void findEmptyShouldReturnAll() {
		
		HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());
		
		ResponseEntity<List<EvidenceViewDto>> response = restTemplate.exchange(
				LOCALHOST + port + SERVICE_PATH, HttpMethod.GET, httpEntity, responseTypeEvidenceView);
		
		assertNotNull(response.getBody());
		assertEquals(TOTAL_EVIDENCE, response.getBody().size());
	}
}
