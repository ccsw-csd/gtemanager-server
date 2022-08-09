package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.StringUtils;

import com.ccsw.gtemanager.evidence.model.UploadDto;
import com.ccsw.gtemanager.person.PersonRepository;

/**
 * EvidenceTest: colección de tests unitarios que prueban funcionalidades de la
 * aplicación backend.
 * 
 * @author cavire
 *
 */
@ExtendWith(MockitoExtension.class)
public class EvidenceTest {

	private static final String EXISTING_RUNDATE = "July 27, 2022 08:30 AM";
	private static final String NONEXISTING_RUNDATE = "asdf";

	private static final String EXISTING_SAGA = "1111_0000Z999";
	private static final String NONEXISTING_SAGA = "9999_0000A111";

	private static final String EXISTING_PERIOD = "29-AUG-2022 - 31-AUG-2022";
	private static final String NONEXISTING_PERIOD = "29-AUG-2022 - 21-AUG-2022";

	private static final String EXISTING_TYPE = "WORKING";
	private static final String NONEXISTING_TYPE = "Completed";

	private static Workbook gteEvidences;
	private static Sheet sheet;
	private static Row row10, row15;
	private static Cell cellRunDate, cellSAGA;

	@Mock
	private EvidenceRepository evidenceRepository;

	@Mock
	private PersonRepository personRepository;

	@InjectMocks
	private DefaultEvidenceService evidenceService;

	/** */
	@BeforeAll
	public static void initializeSpreadsheet() {
		gteEvidences = new XSSFWorkbook();

		sheet = gteEvidences.createSheet("Sheet1");

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

	/**
	 * B10 válido da OK
	 * 
	 * @throws IOException
	 */
	@Test
	public void validRunDateShouldReturnOK() throws IOException {
		cellRunDate.setCellValue(NONEXISTING_RUNDATE);

		UploadDto upload = new UploadDto();
		upload.setFile(new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet()));
		upload.setDeleteComments(false);

		evidenceService.uploadEvidence(upload);
		assertTrue(!evidenceService.getEvidences().isEmpty());
	}

	/**
	 * B10 inválido da error
	 * 
	 * @throws IOException
	 */
	@Test
	public void invalidRunDateShouldReturnError() throws IOException {
		cellRunDate.setCellValue(NONEXISTING_RUNDATE);

		UploadDto upload = new UploadDto();
		upload.setFile(new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet()));
		upload.setDeleteComments(false);

		Exception e = assertThrows(IllegalArgumentException.class, () -> evidenceService.uploadEvidence(upload));
		assertTrue(e.getLocalizedMessage().contains("[runDate]"));
	}

	// Username inválido da error

	/**
	 * Error de proceso debería registrar en evidence_error
	 * 
	 * @throws IOException
	 */
	@Test
	public void failedProcessShouldRegisterEvidenceError() throws IOException {
		cellRunDate.setCellValue(NONEXISTING_RUNDATE);

		UploadDto upload = new UploadDto();
		upload.setFile(new MockMultipartFile("test.xslx", "test.xlsx",
				"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", exportSpreadsheet()));
		upload.setDeleteComments(false);

		evidenceService.uploadEvidence(upload);
		assertTrue(!evidenceService.getEvidenceErrors().isEmpty());
	}

	/** Codsaga existente debería devolver persona */
	@Test
	public void existingSAGAShouldReturnPerson() {
		assertTrue(!personRepository.findPersonBySAGA(EXISTING_SAGA).isEmpty());
	}

	/** Codsaga inexistente debería error */
	@Test
	public void nonexistingSAGAShouldReturnError() {
		assertTrue(personRepository.findPersonBySAGA(NONEXISTING_SAGA).isEmpty());
		assertTrue(!evidenceService.getEvidenceErrors().isEmpty());
	}

	/** Periodo correcto debería devolver semana */
	@Test
	public void existingPeriodShouldReturnWeek() {
		assertTrue(StringUtils.hasText(evidenceService.findWeekForPeriod(EXISTING_PERIOD)));
	}

	/** Periodo incorrecto debería error */
	@Test
	public void nonexistingPeriodShouldReturnError() {
		assertThrows(IllegalArgumentException.class, () -> evidenceService.findWeekForPeriod(NONEXISTING_PERIOD));
	}

	/** Tipo correcto debería devolver registro */
	@Test
	public void existingTypeShouldReturnEvidenceType() {
		assertTrue(!evidenceService.findEvidenceType(EXISTING_TYPE).isEmpty());
	}

	/** Tipo incorrecto debería error */
	@Test
	public void nonexistingTypeShouldReturnError() {
		assertTrue(evidenceService.findEvidenceType(NONEXISTING_TYPE).isEmpty());
	}

	// Semanas sin datos deberían estar en null

	/** Si deleteComments borrar evidence_comment */
	@Test
	public void ifDeleteCommentsEmptyEvidenceComment() {
		evidenceService.emptyComments();
		assertTrue(evidenceService.getEvidenceComments().isEmpty());
	}

	/** evidence borrada al comenzar proceso */
	@Test
	public void beginningProcessShouldEmptyEvidence() {
		evidenceService.emptyEvidences();
		assertTrue(evidenceService.getEvidences().isEmpty());
	}

}
