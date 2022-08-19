package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

/**
 * EvidenceTestIT: colección de tests integrados que prueban funcionalidad del
 * programa y API.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EvidenceIT extends BaseITAbstract {

	private static final String LOCALHOST = "http://localhost:";
	private static final String SERVICE_PATH = "/evidence";

	private static final String EXISTING_RUNDATE = "July 27, 2022 08:30 AM";
	private static final String NONEXISTING_RUNDATE = "asdf";

	private static final String EXISTING_FULLNAME = "Ana Cardo";

	private static final String EXISTING_SAGA = "S_00001";
	private static final String NONEXISTING_SAGA = "S_00A0";

	private static final String EXISTING_EMAIL = "anacardo@example.com";

	private static final String EXISTING_FROMDATE = "01-AUG-2022";
	private static final String EXISTING_TODATE = "31-AUG-2022";
	private static final String NONEXISTING_FROMDATE = "01-SEP-2022";
	private static final String NONEXISTING_TODATE = "31-JUL-2022";

	private static final String EXISTING_PERIOD = "29-AUG-2022 - 31-AUG-2022";
	private static final String NONEXISTING_PERIOD = "31-AUG-2022 - 01-AUG-2022";

	private static final String EXISTING_TYPE = "WORKING";
	private static final String NONEXISTING_TYPE = "Completed";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EvidenceService evidenceService;

	private static Workbook gteEvidences;
	private static Sheet sheet;
	private static Row row0, row1, row2, row9, row14;
	private static Cell cellParametersTitle, cellFromDate, cellToDate, cellRunDate, cellFullName, cellSAGA, cellEmail,
			cellPeriod, cellStatus;

	/** */
	@BeforeAll
	public static void initializeSpreadsheet() {
		gteEvidences = new XSSFWorkbook();

		sheet = gteEvidences.createSheet("Sheet1");

		row0 = sheet.createRow(0);
		cellParametersTitle = row0.createCell(0);
		cellParametersTitle.setCellValue("Parameters");

		row1 = sheet.createRow(1);
		cellFromDate = row1.createCell(1);
		row2 = sheet.createRow(2);
		cellToDate = row2.createCell(1);

		row9 = sheet.createRow(9);
		cellRunDate = row9.createCell(1);

		row14 = sheet.createRow(14);
		cellFullName = row14.createCell(0);
		cellSAGA = row14.createCell(1);
		cellEmail = row14.createCell(2);
		cellPeriod = row14.createCell(9);
		cellStatus = row14.createCell(10);
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

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(test), String.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}

	/** Recibir elemento no válido debería devolver error. */
	@Test
	public void sendingInvalidElementShouldReturnError() {
		String test = "test";

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(test, getHeaders()), String.class);

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

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());
	}

	/**
	 * Recibir archivo excel con fechas válidas debería procesar OK. existing saga,
	 * type
	 * 
	 * @throws IOException
	 */
	@Test
	public void sendingValidSpreadsheetFileShouldReturnOK() throws IOException {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		cellFullName.setCellValue(EXISTING_FULLNAME);
		cellSAGA.setCellValue(EXISTING_SAGA);
		cellEmail.setCellValue(EXISTING_EMAIL);
		cellPeriod.setCellValue(EXISTING_PERIOD);
		cellStatus.setCellValue(EXISTING_TYPE);

		MockMultipartFile file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(null, response.getBody());
		assertEquals(1, evidenceService.getEvidences().size());
	}

	/**
	 * Fechas inválidas resultan error
	 * 
	 * @throws IOException
	 */
	@Test
	public void sendingInvalidSpreadsheetFileShouldReturnError() throws IOException {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(NONEXISTING_RUNDATE);

		cellFullName.setCellValue(EXISTING_FULLNAME);
		cellSAGA.setCellValue(EXISTING_SAGA);
		cellEmail.setCellValue(EXISTING_EMAIL);
		cellPeriod.setCellValue(EXISTING_PERIOD);
		cellStatus.setCellValue(EXISTING_TYPE);

		MockMultipartFile file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(((String) response.getBody()).contains("[date]"));
		assertEquals(0, evidenceService.getEvidences().size());

		cellFromDate.setCellValue(NONEXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(((String) response.getBody()).contains("[date]"));
		assertEquals(0, evidenceService.getEvidences().size());

		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(NONEXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertTrue(((String) response.getBody()).contains("[date]"));
		assertEquals(0, evidenceService.getEvidences().size());

	}

	/**
	 * Error de proceso debería registrar en evidence_error. nonexisting saga,
	 * period, type
	 * 
	 * @throws IOException
	 */
	@Test
	public void failedProcessShouldRegisterEvidenceError() throws IOException {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		cellFullName.setCellValue(EXISTING_FULLNAME);
		cellSAGA.setCellValue(NONEXISTING_SAGA);
		cellEmail.setCellValue(EXISTING_EMAIL);
		cellPeriod.setCellValue(EXISTING_PERIOD);
		cellStatus.setCellValue(EXISTING_TYPE);

		MockMultipartFile file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		HttpHeaders headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(((String) response.getBody()).contains("[errors]"));
		assertEquals(0, evidenceService.getEvidences().size());

		cellFullName.setCellValue(EXISTING_FULLNAME);
		cellSAGA.setCellValue(EXISTING_SAGA);
		cellEmail.setCellValue(EXISTING_EMAIL);
		cellPeriod.setCellValue(NONEXISTING_PERIOD);
		cellStatus.setCellValue(EXISTING_TYPE);

		file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(((String) response.getBody()).contains("[errors]"));
		assertEquals(0, evidenceService.getEvidences().size());
		assertEquals(1, evidenceService.getEvidenceErrors().size());

		cellFullName.setCellValue(EXISTING_FULLNAME);
		cellSAGA.setCellValue(EXISTING_SAGA);
		cellEmail.setCellValue(EXISTING_EMAIL);
		cellPeriod.setCellValue(EXISTING_PERIOD);
		cellStatus.setCellValue(NONEXISTING_TYPE);

		file = new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet());

		body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("deleteComments", false);

		headers = getHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(body, headers), String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(((String) response.getBody()).contains("[errors]"));
		assertEquals(0, evidenceService.getEvidences().size());
		assertEquals(1, evidenceService.getEvidenceErrors().size());
	}

	// Semanas sin datos deberían estar en null

}
