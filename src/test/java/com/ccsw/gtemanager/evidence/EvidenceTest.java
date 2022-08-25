package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.gtemanager.evidencecomment.EvidenceCommentRepository;
import com.ccsw.gtemanager.evidencecomment.EvidenceCommentServiceImpl;
import com.ccsw.gtemanager.evidenceerror.EvidenceErrorRepository;
import com.ccsw.gtemanager.evidenceerror.EvidenceErrorServiceImpl;
import com.ccsw.gtemanager.person.PersonServiceImpl;
import com.ccsw.gtemanager.properties.PropertiesRepository;
import com.ccsw.gtemanager.properties.PropertiesServiceImpl;

/**
 * EvidenceTest: colección de tests unitarios que prueban funcionalidades de la
 * aplicación backend.
 */
@ExtendWith(MockitoExtension.class)
public class EvidenceTest {

	private static final String EXISTING_PERIOD = "29-AUG-2022 - 31-AUG-2022";
	private static final String EXISTING_PERIOD_WEEK = "29-AUG-2022 - 04-SEP-2022";
	private static final String NONEXISTING_PERIOD = "29-AUG-2022 - 21-AUG-2022";

	private static final String EXISTING_DAY1 = "15-FEB-2021";
	private static final String EXISTING_DAY2 = "29-AUG-2022";
	private static final String EXISTING_DAY3 = "02-OCT-2022";

	private static final String EXISTING_UNPARSED_SAGA = "S_000A2";
	private static final String NONEXISTING_UNPARSED_SAGA = "FD242";
	private static final String EXISTING_SAGA = "00A2";

	private static final int EXISTING_MONTH1_WEEKS = 4;
	private static final int EXISTING_MONTH2_WEEKS = 5;
	private static final int EXISTING_MONTH3_WEEKS = 6;
	private static final String EXISTING_MONTH1_WEEK1 = "01-FEB-2021 - 07-FEB-2021";
	private static final String EXISTING_MONTH2_WEEK3 = "15-AUG-2022 - 21-AUG-2022";
	private static final String EXISTING_MONTH3_WEEK6 = "31-OCT-2022 - 06-NOV-2022";

	@Mock
	private EvidenceRepository evidenceRepository;

	@Mock
	private EvidenceErrorRepository evidenceErrorRepository;

	@Mock
	private EvidenceCommentRepository evidenceCommentRepository;

	@Mock
	private PropertiesRepository propertiesRepository;

	@InjectMocks
	private EvidenceServiceImpl evidenceService;

	@InjectMocks
	private EvidenceErrorServiceImpl evidenceErrorService;

	@InjectMocks
	private EvidenceCommentServiceImpl evidenceCommentService;

	@InjectMocks
	private PersonServiceImpl personService;

	@InjectMocks
	private PropertiesServiceImpl propertiesService;

	private static DateTimeFormatter format = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

	/**
	 * Un código saga válido debe poder procesarse.
	 */
	@Test
	public void parseValidSagaShouldReturnSaga() {
		assertEquals(EXISTING_SAGA, personService.parseSaga(EXISTING_UNPARSED_SAGA));
	}

	/**
	 * Un código saga inválido debe resultar en error de lectura.
	 */
	@Test
	public void parseInvalidSagaShouldReturnError() {
		assertThrows(IllegalArgumentException.class, () -> personService.parseSaga(NONEXISTING_UNPARSED_SAGA));
	}

	/**
	 * Se debe obtener el número y nombre apropiado de semanas en un mes.
	 */
	@Test
	public void obtainWeeksShouldReturnWeeksOfMonth() {
		List<String> weeks1 = evidenceService.obtainWeeks(LocalDate.parse(EXISTING_DAY1, format));
		List<String> weeks2 = evidenceService.obtainWeeks(LocalDate.parse(EXISTING_DAY2, format));
		List<String> weeks3 = evidenceService.obtainWeeks(LocalDate.parse(EXISTING_DAY3, format));

		assertEquals(EXISTING_MONTH1_WEEKS, weeks1.size());
		assertEquals(EXISTING_MONTH2_WEEKS, weeks2.size());
		assertEquals(EXISTING_MONTH3_WEEKS, weeks3.size());

		assertEquals(EXISTING_MONTH1_WEEK1, weeks1.get(0));
		assertEquals(EXISTING_MONTH2_WEEK3, weeks2.get(2));
		assertEquals(EXISTING_MONTH3_WEEK6, weeks3.get(5));
	}

	/**
	 * Se debe obtener la semana dado un día.
	 */
	@Test
	public void findWeekForValidDayShouldReturnWeek() {
		assertEquals(EXISTING_PERIOD_WEEK, evidenceService.findWeekForDay(LocalDate.parse(EXISTING_DAY2, format)));
	}

	/**
	 * Periodo correcto debería devolver semana.
	 */
	@Test
	public void existingPeriodShouldReturnWeek() {
		assertEquals(EXISTING_PERIOD_WEEK, evidenceService.findWeekForPeriod(EXISTING_PERIOD));
	}

	/**
	 * Periodo incorrecto debería devolver error.
	 */
	@Test
	public void nonexistingPeriodShouldReturnError() {
		assertThrows(IllegalArgumentException.class, () -> evidenceService.findWeekForPeriod(NONEXISTING_PERIOD));
	}

	/**
	 * Si deleteComments es true, borrar EvidenceComment.
	 */
	@Test
	public void ifDeleteCommentsEmptyEvidenceComment() {
		evidenceService.clearEvidenceData(true);
		assertTrue(evidenceCommentService.getAll().isEmpty());
	}

	/**
	 * Comprobar que datos de evidencias son borrados al comenzar el proceso.
	 */
	@Test
	public void beginningProcessShouldEmptyEvidence() {
		evidenceService.clearEvidenceData(false);
		assertTrue(evidenceService.getAll().isEmpty());
		assertTrue(evidenceErrorService.getAll().isEmpty());
		assertTrue(propertiesService.getAll().isEmpty());
	}
}
