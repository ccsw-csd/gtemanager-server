package com.ccsw.gtemanager.evidence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
