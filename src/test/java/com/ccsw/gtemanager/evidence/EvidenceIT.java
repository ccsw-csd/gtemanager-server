package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.ccsw.gtemanager.evidence.model.Evidence;
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
import org.springframework.core.ParameterizedTypeReference;
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
import com.ccsw.gtemanager.evidence.model.EvidenceDto;
import com.ccsw.gtemanager.evidenceerror.EvidenceErrorService;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;

/**
 * EvidenceIT: colección de tests integrados que prueban funcionalidad del
 * programa y API.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EvidenceIT extends BaseITAbstract {

    private static final int INITIAL_EVIDENCES_SIZE = 6;

    private static final String SHEET_NAME = "Sheet1";

    private static final int ROW_PROPERTY_FROM_DATE = 1;
    private static final int ROW_PROPERTY_TO_DATE = 2;
    private static final int ROW_PROPERTY_RUNDATE = 9;

    private static final int ROW_EVIDENCE_LIST_FIRST = 14;
    private static final int ROW_EVIDENCE_LIST_SECOND = 15;
    private static final int ROW_EVIDENCE_LIST_THIRD = 16;

    private static final int COL_PROPERTY_VALUE = 1;

    private static final int COL_EVIDENCE_NAME = 0;
    private static final int COL_EVIDENCE_SAGA = 1;
    private static final int COL_EVIDENCE_EMAIL = 2;
    private static final int COL_EVIDENCE_PERIOD = 9;
    private static final int COL_EVIDENCE_STATUS = 10;
    private static final int COL_EVIDENCE_MESSAGE = 0;

    private static final String FILE_VARIABLE = "file";
    private static final String DELETE_COMMENTS_VARIABLE = "deleteComments";
    private static final String DELETE_COLORS_VARIABLE = "deleteColors";

    private static final String TEST_STRING = "test";

    private static final String PDF_FILE_NAME = "test.pdf";
    private static final String XLSX_FILE_NAME = "test.xlsx";
    private static final String XLSX_FILE_FORMAT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

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

    private static final String EXISTING_MESSAGE = "No existe persona con el código saga especificado.";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private EvidenceErrorService evidenceErrorService;

    private static Workbook gteEvidences;
    private static Sheet sheet;

    private static Row rowFirstEvidence;
    private static Row rowSecondEvidence;
    private static Row rowThirdEvidence;

    private static Cell cellFromDate;
    private static Cell cellToDate;
    private static Cell cellRunDate;

    private static Cell[] cellsRowFirstEvidence;
    private static Cell[] cellsRowSecondEvidence;
    private static Cell[] cellsRowThirdEvidence;

    ParameterizedTypeReference<List<EvidenceDto>> responseTypeEvidence = new ParameterizedTypeReference<List<EvidenceDto>>() {
    };

    /**
     * Inicializar hoja de cálculo y celdas de valores previo a la ejecución de los
     * tests.
     */
    @BeforeEach
    public void initializeSpreadsheet() {
        gteEvidences = new XSSFWorkbook();

        sheet = gteEvidences.createSheet(SHEET_NAME);

        cellFromDate = sheet.createRow(ROW_PROPERTY_FROM_DATE).createCell(COL_PROPERTY_VALUE);
        cellToDate = sheet.createRow(ROW_PROPERTY_TO_DATE).createCell(COL_PROPERTY_VALUE);
        cellRunDate = sheet.createRow(ROW_PROPERTY_RUNDATE).createCell(COL_PROPERTY_VALUE);

        rowFirstEvidence = sheet.createRow(ROW_EVIDENCE_LIST_FIRST);
        cellsRowFirstEvidence = new Cell[6];
        cellsRowFirstEvidence[0] = rowFirstEvidence.createCell(COL_EVIDENCE_NAME);
        cellsRowFirstEvidence[1] = rowFirstEvidence.createCell(COL_EVIDENCE_SAGA);
        cellsRowFirstEvidence[2] = rowFirstEvidence.createCell(COL_EVIDENCE_EMAIL);
        cellsRowFirstEvidence[3] = rowFirstEvidence.createCell(COL_EVIDENCE_PERIOD);
        cellsRowFirstEvidence[4] = rowFirstEvidence.createCell(COL_EVIDENCE_STATUS);
        cellsRowFirstEvidence[5] = rowFirstEvidence.createCell(COL_EVIDENCE_MESSAGE);

        rowSecondEvidence = sheet.createRow(ROW_EVIDENCE_LIST_SECOND);
        cellsRowSecondEvidence = new Cell[6];
        cellsRowSecondEvidence[0] = rowSecondEvidence.createCell(COL_EVIDENCE_NAME);
        cellsRowSecondEvidence[1] = rowSecondEvidence.createCell(COL_EVIDENCE_SAGA);
        cellsRowSecondEvidence[2] = rowSecondEvidence.createCell(COL_EVIDENCE_EMAIL);
        cellsRowSecondEvidence[3] = rowSecondEvidence.createCell(COL_EVIDENCE_PERIOD);
        cellsRowSecondEvidence[4] = rowSecondEvidence.createCell(COL_EVIDENCE_STATUS);
        cellsRowSecondEvidence[5] = rowFirstEvidence.createCell(COL_EVIDENCE_MESSAGE);

        rowThirdEvidence = sheet.createRow(ROW_EVIDENCE_LIST_THIRD);
        cellsRowThirdEvidence = new Cell[6];
        cellsRowThirdEvidence[0] = rowThirdEvidence.createCell(COL_EVIDENCE_NAME);
        cellsRowThirdEvidence[1] = rowThirdEvidence.createCell(COL_EVIDENCE_SAGA);
        cellsRowThirdEvidence[2] = rowThirdEvidence.createCell(COL_EVIDENCE_EMAIL);
        cellsRowThirdEvidence[3] = rowThirdEvidence.createCell(COL_EVIDENCE_PERIOD);
        cellsRowThirdEvidence[4] = rowThirdEvidence.createCell(COL_EVIDENCE_STATUS);
        cellsRowThirdEvidence[5] = rowFirstEvidence.createCell(COL_EVIDENCE_MESSAGE);
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
        String test = TEST_STRING;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(test), String.class);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    /**
     * Recibir elemento no válido debería devolver error.
     */
    @Test
    public void sendingInvalidElementShouldReturnError() {
        String test = TEST_STRING;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(test, getHeaders()), String.class);

        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }

    /**
     * Recibir archivo no excel debería devolver error.
     */
    @Test
    public void sendingNonSpreadsheetFileShouldReturnError() {
        MockMultipartFile file = new MockMultipartFile(PDF_FILE_NAME, PDF_FILE_NAME, MediaType.APPLICATION_PDF_VALUE,
                TEST_STRING.getBytes());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

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

        cellsRowFirstEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowFirstEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowFirstEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowFirstEvidence[3].setCellValue(EXISTING_PERIOD_W4);
        cellsRowFirstEvidence[4].setCellValue(EXISTING_TYPE_1);
        cellsRowFirstEvidence[5].setCellValue(EXISTING_MESSAGE);

        cellsRowSecondEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowSecondEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowSecondEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowSecondEvidence[3].setCellValue(EXISTING_PERIOD_W5);
        cellsRowSecondEvidence[4].setCellValue(EXISTING_TYPE_2);
        cellsRowSecondEvidence[5].setCellValue(EXISTING_MESSAGE);

        cellsRowThirdEvidence[0].setCellValue(EXISTING_FULLNAME_P2);
        cellsRowThirdEvidence[1].setCellValue(EXISTING_SAGA_P2);
        cellsRowThirdEvidence[2].setCellValue(EXISTING_EMAIL_P2);
        cellsRowThirdEvidence[3].setCellValue(EXISTING_PERIOD_W5);
        cellsRowThirdEvidence[4].setCellValue(EXISTING_TYPE_1);
        cellsRowThirdEvidence[5].setCellValue(EXISTING_MESSAGE);

        MockMultipartFile file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT,
                exportSpreadsheet());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
        assertEquals(2, evidenceService.getEvidences().size());
    }

    /**
     * Datos inválidos deberían abortar proceso de lectura de datos.
     */
    @Test
    public void sendingInvalidSpreadsheetFileShouldReturnError() {
        cellFromDate.setCellValue(EXISTING_FROMDATE);
        cellToDate.setCellValue(EXISTING_TODATE);
        cellRunDate.setCellValue(NONEXISTING_RUNDATE);

        cellsRowFirstEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowFirstEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowFirstEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowFirstEvidence[3].setCellValue(EXISTING_PERIOD_W5);
        cellsRowFirstEvidence[4].setCellValue(EXISTING_TYPE_1);

        MockMultipartFile file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT,
                exportSpreadsheet());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        cellFromDate.setCellValue(NONEXISTING_FROMDATE);
        cellToDate.setCellValue(EXISTING_TODATE);
        cellRunDate.setCellValue(EXISTING_RUNDATE);

        file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT, exportSpreadsheet());

        body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotEquals(null, response.getBody());
        assertEquals(1, evidenceService.getEvidences().size());

        cellFromDate.setCellValue(EXISTING_FROMDATE);
        cellToDate.setCellValue(NONEXISTING_TODATE);
        cellRunDate.setCellValue(EXISTING_RUNDATE);

        file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT, exportSpreadsheet());

        body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotEquals(null, response.getBody());
        assertEquals(1, evidenceService.getEvidences().size());
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

        cellsRowFirstEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowFirstEvidence[1].setCellValue(NONEXISTING_SAGA);
        cellsRowFirstEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowFirstEvidence[3].setCellValue(EXISTING_PERIOD_W4);
        cellsRowFirstEvidence[4].setCellValue(EXISTING_TYPE_1);
        cellsRowFirstEvidence[5].setCellValue(EXISTING_MESSAGE);

        cellsRowSecondEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowSecondEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowSecondEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowSecondEvidence[3].setCellValue(EXISTING_PERIOD_W5);
        cellsRowSecondEvidence[4].setCellValue(EXISTING_TYPE_2);
        cellsRowSecondEvidence[5].setCellValue(EXISTING_MESSAGE);

        cellsRowThirdEvidence[0].setCellValue(EXISTING_FULLNAME_P2);
        cellsRowThirdEvidence[1].setCellValue(EXISTING_SAGA_P2);
        cellsRowThirdEvidence[2].setCellValue(EXISTING_EMAIL_P2);
        cellsRowThirdEvidence[3].setCellValue(EXISTING_PERIOD_W5);
        cellsRowThirdEvidence[4].setCellValue(EXISTING_TYPE_1);
        cellsRowThirdEvidence[5].setCellValue(EXISTING_MESSAGE);

        MockMultipartFile file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT,
                exportSpreadsheet());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        cellsRowFirstEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowFirstEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowFirstEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowFirstEvidence[3].setCellValue(NONEXISTING_PERIOD);
        cellsRowFirstEvidence[4].setCellValue(EXISTING_TYPE_1);

        file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT, exportSpreadsheet());

        body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(null, response.getBody());
        assertEquals(2, evidenceService.getEvidences().size());
        assertEquals(1, evidenceErrorService.getEvidenceErrors().size());

        cellsRowFirstEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowFirstEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowFirstEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowFirstEvidence[3].setCellValue(EXISTING_PERIOD_W4);
        cellsRowFirstEvidence[4].setCellValue(NONEXISTING_TYPE);

        file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT, exportSpreadsheet());

        body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotEquals(null, response.getBody());
        assertEquals(2, evidenceService.getEvidences().size());
        assertEquals(1, evidenceErrorService.getEvidenceErrors().size());
    }

    /**
     * Semanas sin datos deberían estar en null.
     */
    @Test
    public void weeksWithoutEvidencesShouldBeNull() {
        cellFromDate.setCellValue(EXISTING_FROMDATE);
        cellToDate.setCellValue(EXISTING_TODATE);
        cellRunDate.setCellValue(EXISTING_RUNDATE);

        cellsRowFirstEvidence[0].setCellValue(EXISTING_FULLNAME_P1);
        cellsRowFirstEvidence[1].setCellValue(EXISTING_SAGA_P1);
        cellsRowFirstEvidence[2].setCellValue(EXISTING_EMAIL_P1);
        cellsRowFirstEvidence[3].setCellValue(EXISTING_PERIOD_W5);
        cellsRowFirstEvidence[4].setCellValue(EXISTING_TYPE_1);

        MockMultipartFile file = new MockMultipartFile(XLSX_FILE_NAME, XLSX_FILE_NAME, XLSX_FILE_FORMAT,
                exportSpreadsheet());

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add(FILE_VARIABLE, file.getResource());
        body.add(DELETE_COMMENTS_VARIABLE, false);
        body.add(DELETE_COLORS_VARIABLE, false);

        HttpHeaders headers = getHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST,
                new HttpEntity<>(body, headers), String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(null, response.getBody());
        assertEquals(1, evidenceService.getEvidences().size());

        assertEquals(null, evidenceService.getEvidences().get(0).getEvidenceTypeW1());
        assertEquals(null, evidenceService.getEvidences().get(0).getEvidenceTypeW2());
        assertEquals(null, evidenceService.getEvidences().get(0).getEvidenceTypeW3());
        assertEquals(null, evidenceService.getEvidences().get(0).getEvidenceTypeW4());
        EvidenceType evidenceType = new EvidenceType(EXISTING_TYPE_1);
        assertEquals(evidenceType.getCode(), evidenceService.getEvidences().get(0).getEvidenceTypeW5().getCode());
        assertEquals(null, evidenceService.getEvidences().get(0).getEvidenceTypeW6());
    }
}