package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.ccsw.gtemanager.config.BaseITAbstract;
import com.ccsw.gtemanager.person.PersonRepository;

/**
 * EvidenceTestIT: colección de tests integrados que prueban funcionalidad del
 * programa y API.
 * 
 * @author cavire
 *
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EvidenceTestIT extends BaseITAbstract {

	private static final String LOCALHOST = "http://localhost:";
	private static final String SERVICE_PATH = "/evidence";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	private static Workbook gteEvidences;
	private static Sheet sheet;
	private static Row row0, row10, row15;
	private static Cell cellParametersTitle, cellRunDate, cellSAGA;

	/** */
	@BeforeAll
	public static void initializeSpreadsheet() {
		gteEvidences = new XSSFWorkbook();

		sheet = gteEvidences.createSheet("Sheet1");

		row0 = sheet.createRow(0);
		cellParametersTitle = row0.createCell(0);
		cellParametersTitle.setCellValue("Parameters");

		row10 = sheet.createRow(9);
		cellRunDate = row10.createCell(1);

		row15 = sheet.createRow(14);
		cellSAGA = row15.createCell(1);
	}

	/** */
	private byte[] exportSpreadsheet() throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		gteEvidences.write(baos);
		return baos.toByteArray();
	}

	/** Acceso sin token debería devolver error. */
	@Test
	public void sendingElementWithoutTokenShouldReturnError() {
		String test = "test";

		HttpEntity<?> httpEntity = new HttpEntity<>(test);

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}

	/** Recibir elemento no válido debería devolver error. */
	@Test
	public void sendingInvalidElementShouldReturnError() {
		String test = "test";

		HttpEntity<?> httpEntity = new HttpEntity<>(test, getHeaders());

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
	}

	/** Recibir archivo no excel debería devolver error. */
	@Test
	public void sendingNonSpreadsheetFileShouldReturnError() {
		MockMultipartFile file = new MockMultipartFile("test.pdf", "test.pdf", MediaType.APPLICATION_PDF_VALUE,
				"test".getBytes());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	/**
	 * Recibir archivo excel debería procesar OK
	 * 
	 * @throws IOException
	 */
	@Test
	public void sendingSpreadsheetFileShouldReturnOK() throws IOException {
		MockMultipartFile file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<?> httpEntity = new HttpEntity<>(body, headers);

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, httpEntity,
				String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
