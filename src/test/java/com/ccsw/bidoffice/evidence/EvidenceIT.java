package com.ccsw.bidoffice.evidence;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.bidoffice.config.BaseITAbstract;
import com.ccsw.gtemanager.evidence.model.EvidenceDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EvidenceIT extends BaseITAbstract {
	
	public static final String SERVICE_PATH = "/evidence/";
	
	ParameterizedTypeReference<List<EvidenceDto>> responseTypeEvidence = new ParameterizedTypeReference<List<EvidenceDto>>() {
    };
	
	@Test
	public void findEmptyShouldReturnEveryEvidence() {
		HttpEntity<?> httpEntity = new HttpEntity<>(getHeaders());
		
		ResponseEntity<List<EvidenceDto>> response = restTemplate.exchange(
				LOCALHOST + port + SERVICE_PATH + "find", HttpMethod.GET, httpEntity, responseTypeEvidence);
	}
}