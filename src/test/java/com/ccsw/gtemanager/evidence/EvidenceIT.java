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
import org.junit.jupiter.api.BeforeEach;
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
import com.ccsw.gtemanager.evidenceerror.EvidenceErrorService;
import com.ccsw.gtemanager.evidencetype.EvidenceTypeService;

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

	private static final String EXISTING_FULLNAME_P1 = "Ana Cardo";
	private static final String EXISTING_FULLNAME_P2 = "Aitor Tilla";

	private static final String EXISTING_SAGA_P1 = "S_00001";
	private static final String EXISTING_SAGA_P2 = "S_000B1";
	private static final String NONEXISTING_SAGA = "S_00A0";

	private static final String EXISTING_EMAIL_P1 = "anacardo@example.com";
	private static final String EXISTING_EMAIL_P2 = "aitortilla@example.com";

	private static final String EXISTING_FROMDATE = "01-AUG-2022";
	private static final String EXISTING_TODATE = "31-AUG-2022";
	private static final String NONEXISTING_FROMDATE = "01-SEP-2022";
	private static final String NONEXISTING_TODATE = "31-JUL-2022";

	private static final String EXISTING_PERIOD_W4 = "22-AUG-2022 - 28-AUG-2022";
	private static final String EXISTING_PERIOD_W5 = "29-AUG-2022 - 31-AUG-2022";
	private static final String NONEXISTING_PERIOD = "31-AUG-2022 - 01-AUG-2022";

	private static final String EXISTING_TYPE_1 = "WORKING";
	private static final String EXISTING_TYPE_2 = "Missing";
	private static final String NONEXISTING_TYPE = "Completed";

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EvidenceService evidenceService;

	@Autowired
	private EvidenceErrorService evidenceErrorService;

	@Autowired
	private EvidenceTypeService evidenceTypeService;

	private static Workbook gteEvidences;
	private static Sheet sheet;

	private static Row row1;
	private static Row row2;
	private static Row row9;
	private static Row row14;
	private static Row row15;
	private static Row row16;

	private static Cell cellFromDate;
	private static Cell cellToDate;
	private static Cell cellRunDate;

	private static Cell[] cellsRow14;
	private static Cell[] cellsRow15;
	private static Cell[] cellsRow16;

	/**
	 * Inicializar hoja de cálculo y celdas de valores previo a la ejecución de los
	 * tests.
	 */
	@BeforeEach
	public void initializeSpreadsheet() {
		gteEvidences = new XSSFWorkbook();

		sheet = gteEvidences.createSheet("Sheet1");

		row1 = sheet.createRow(1);
		cellFromDate = row1.createCell(1);
		row2 = sheet.createRow(2);
		cellToDate = row2.createCell(1);

		row9 = sheet.createRow(9);
		cellRunDate = row9.createCell(1);

		row14 = sheet.createRow(14);
		cellsRow14 = new Cell[5];
		cellsRow14[0] = row14.createCell(0);
		cellsRow14[1] = row14.createCell(1);
		cellsRow14[2] = row14.createCell(2);
		cellsRow14[3] = row14.createCell(9);
		cellsRow14[4] = row14.createCell(10);

		row15 = sheet.createRow(15);
		cellsRow15 = new Cell[5];
		cellsRow15[0] = row15.createCell(0);
		cellsRow15[1] = row15.createCell(1);
		cellsRow15[2] = row15.createCell(2);
		cellsRow15[3] = row15.createCell(9);
		cellsRow15[4] = row15.createCell(10);

		row16 = sheet.createRow(16);
		cellsRow16 = new Cell[5];
		cellsRow16[0] = row16.createCell(0);
		cellsRow16[1] = row16.createCell(1);
		cellsRow16[2] = row16.createCell(2);
		cellsRow16[3] = row16.createCell(9);
		cellsRow16[4] = row16.createCell(10);
	}

	/**
	 * Obtener elemento exportado a partir de la hoja de cálculo.
	 * 
	 * @return Hoja de cálculo en array de bytes (byte[])
	 */
	private byte[] exportSpreadsheet() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			gteEvidences.write(baos);
			return baos.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * Acceso sin token válido de usuario debería devolver error.
	 */
	@Test
	public void sendingElementWithoutTokenShouldReturnError() {
		String test = "test";

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(test), String.class);

		assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
	}

	/**
	 * Recibir elemento no válido debería devolver error.
	 */
	@Test
	public void sendingInvalidElementShouldReturnError() {
		String test = "test";

		ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
				new HttpEntity<>(test, getHeaders()), String.class);

		assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
	}

	/**
	 * Recibir archivo no excel debería devolver error.
	 */
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
	 * Recibir archivo excel con todos los datos válidos debería procesarse.
	 */
	@Test
	public void sendingValidSpreadsheetFileShouldReturnOK() {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		cellsRow14[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow14[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow14[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow14[3].setCellValue(EXISTING_PERIOD_W4);
		cellsRow14[4].setCellValue(EXISTING_TYPE_1);

		cellsRow15[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow15[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow15[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow15[3].setCellValue(EXISTING_PERIOD_W5);
		cellsRow15[4].setCellValue(EXISTING_TYPE_2);

		cellsRow16[0].setCellValue(EXISTING_FULLNAME_P2);
		cellsRow16[1].setCellValue(EXISTING_SAGA_P2);
		cellsRow16[2].setCellValue(EXISTING_EMAIL_P2);
		cellsRow16[3].setCellValue(EXISTING_PERIOD_W5);
		cellsRow16[4].setCellValue(EXISTING_TYPE_1);

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
		assertEquals(2, evidenceService.getAll().size());
	}

	/**
	 * Datos inválidos deberían abortar proceso de lectura de datos.
	 */
	@Test
	public void sendingInvalidSpreadsheetFileShouldReturnError() {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(NONEXISTING_RUNDATE);

		cellsRow14[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow14[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow14[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow14[3].setCellValue(EXISTING_PERIOD_W5);
		cellsRow14[4].setCellValue(EXISTING_TYPE_1);

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
		assertTrue(response.getBody() != null);
		assertEquals(0, evidenceService.getAll().size());

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
		assertTrue(response.getBody() != null);
		assertEquals(0, evidenceService.getAll().size());

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
		assertTrue(response.getBody() != null);
		assertEquals(0, evidenceService.getAll().size());
	}

	/**
	 * Error de proceso por valores incorrectos en evidencias debe procesar con
	 * errores (debe registrar en EvidenceError).
	 */
	@Test
	public void failedProcessShouldRegisterEvidenceError() {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		cellsRow14[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow14[1].setCellValue(NONEXISTING_SAGA);
		cellsRow14[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow14[3].setCellValue(EXISTING_PERIOD_W4);
		cellsRow14[4].setCellValue(EXISTING_TYPE_1);

		cellsRow15[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow15[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow15[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow15[3].setCellValue(EXISTING_PERIOD_W5);
		cellsRow15[4].setCellValue(EXISTING_TYPE_2);

		cellsRow16[0].setCellValue(EXISTING_FULLNAME_P2);
		cellsRow16[1].setCellValue(EXISTING_SAGA_P2);
		cellsRow16[2].setCellValue(EXISTING_EMAIL_P2);
		cellsRow16[3].setCellValue(EXISTING_PERIOD_W5);
		cellsRow16[4].setCellValue(EXISTING_TYPE_1);

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
		assertTrue(response.getBody() != null);
		assertEquals(2, evidenceService.getAll().size());

		cellsRow14[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow14[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow14[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow14[3].setCellValue(NONEXISTING_PERIOD);
		cellsRow14[4].setCellValue(EXISTING_TYPE_1);

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
		assertTrue(!response.getBody().equals(null));
		assertEquals(2, evidenceService.getAll().size());
		assertEquals(1, evidenceErrorService.getAll().size());

		cellsRow14[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow14[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow14[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow14[3].setCellValue(EXISTING_PERIOD_W4);
		cellsRow14[4].setCellValue(NONEXISTING_TYPE);

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
		assertTrue(!response.getBody().equals(null));
		assertEquals(2, evidenceService.getAll().size());
		assertEquals(1, evidenceErrorService.getAll().size());
	}

	/**
	 * Semanas sin datos deberían estar en null.
	 */
	@Test
	public void weeksWithoutEvidencesShouldBeNull() {
		cellFromDate.setCellValue(EXISTING_FROMDATE);
		cellToDate.setCellValue(EXISTING_TODATE);
		cellRunDate.setCellValue(EXISTING_RUNDATE);

		cellsRow14[0].setCellValue(EXISTING_FULLNAME_P1);
		cellsRow14[1].setCellValue(EXISTING_SAGA_P1);
		cellsRow14[2].setCellValue(EXISTING_EMAIL_P1);
		cellsRow14[3].setCellValue(EXISTING_PERIOD_W5);
		cellsRow14[4].setCellValue(EXISTING_TYPE_1);

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
		assertEquals(1, evidenceService.getAll().size());

		assertEquals(null, evidenceService.getAll().get(0).getEvidenceTypeW1());
		assertEquals(null, evidenceService.getAll().get(0).getEvidenceTypeW2());
		assertEquals(null, evidenceService.getAll().get(0).getEvidenceTypeW3());
		assertEquals(null, evidenceService.getAll().get(0).getEvidenceTypeW4());
		assertEquals(evidenceTypeService.getByCode(EXISTING_TYPE_1).getCode(),
				evidenceService.getAll().get(0).getEvidenceTypeW5().getCode());
		assertEquals(null, evidenceService.getAll().get(0).getEvidenceTypeW6());
	}
}
