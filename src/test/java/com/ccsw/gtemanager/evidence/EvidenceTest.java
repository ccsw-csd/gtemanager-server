package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.gtemanager.properties.PropertiesRepository;

/**
 * EvidenceTest: colección de tests unitarios que prueban funcionalidades de la
 * aplicación backend.
 * 
 * @author cavire
 *
 */
@ExtendWith(MockitoExtension.class)
public class EvidenceTest {

	private static final String EXISTING_PERIOD = "29-AUG-2022 - 31-AUG-2022";
	private static final String EXISTING_PERIOD_WEEK = "29-AUG-2022 - 04-SEP-2022";
	private static final String NONEXISTING_PERIOD = "29-AUG-2022 - 21-AUG-2022";

	private static final String EXISTING_DAY_FEB = "15-FEB-2021";
	private static final String EXISTING_DAY_AUG = "29-AUG-2022";
	private static final String EXISTING_DAY_OCT = "02-OCT-2022";

	private static final String EXISTING_UNPARSED_SAGA = "S_000A2";
	private static final String NONEXISTING_UNPARSED_SAGA = "FD242";
	private static final String EXISTING_SAGA = "00A2";

	@Mock
	private EvidenceRepository evidenceRepository;

	@Mock
	private EvidenceErrorRepository evidenceErrorRepository;

	@Mock
	private EvidenceCommentRepository evidenceCommentRepository;

	@Mock
	private PropertiesRepository propertiesRepository;

	@InjectMocks
	private DefaultEvidenceService evidenceService;

	private static DateTimeFormatter format = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

	@Test
	public void parseValidSagaShouldReturnSaga() {
		assertEquals(EXISTING_SAGA, evidenceService.parseSaga(EXISTING_UNPARSED_SAGA));
	}

	@Test
	public void parseInvalidSagaShouldReturnError() {
		assertThrows(IllegalArgumentException.class, () -> evidenceService.parseSaga(NONEXISTING_UNPARSED_SAGA));
	}

	@Test
	public void obtainWeeksShouldReturnWeeksOfMonth() {
		assertEquals(4, evidenceService.obtainWeeks(LocalDate.parse(EXISTING_DAY_FEB, format)).size());
		assertEquals(5, evidenceService.obtainWeeks(LocalDate.parse(EXISTING_DAY_AUG, format)).size());
		assertEquals(6, evidenceService.obtainWeeks(LocalDate.parse(EXISTING_DAY_OCT, format)).size());
	}

	@Test
	public void findWeekForValidDayShouldReturnWeek() {
		assertEquals(EXISTING_PERIOD_WEEK, evidenceService.findWeekForDay(LocalDate.parse(EXISTING_DAY_AUG, format)));
	}

	/** Periodo correcto debería devolver semana */
	@Test
	public void existingPeriodShouldReturnWeek() {
		assertEquals(EXISTING_PERIOD_WEEK, evidenceService.findWeekForPeriod(EXISTING_PERIOD));
	}

	/** Periodo incorrecto debería error */
	@Test
	public void nonexistingPeriodShouldReturnError() {
		assertThrows(IllegalArgumentException.class, () -> evidenceService.findWeekForPeriod(NONEXISTING_PERIOD));
	}

	/** Si deleteComments borrar evidence_comment */
	@Test
	public void ifDeleteCommentsEmptyEvidenceComment() {
		evidenceService.clearEvidenceData(true);
		assertTrue(evidenceService.getEvidenceComments().isEmpty());
	}

	/** evidence borrada al comenzar proceso */
	@Test
	public void beginningProcessShouldEmptyEvidence() {
		evidenceService.clearEvidenceData(false);
		assertTrue(evidenceService.getEvidences().isEmpty());
		assertTrue(evidenceService.getEvidenceErrors().isEmpty());
		assertTrue(evidenceService.getProperties().isEmpty());
	}
}
