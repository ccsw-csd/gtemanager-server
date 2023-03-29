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

import com.ccsw.gtemanager.comment.CommentRepository;
import com.ccsw.gtemanager.comment.CommentServiceImpl;
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

    private static final int WEEK_LIST_1 = 0;
    private static final int WEEK_LIST_3 = 2;
    private static final int WEEK_LIST_6 = 5;

    private static final String EXISTING_PERIOD = "29-AUG-2022 - 31-AUG-2022";
    private static final String EXISTING_PERIOD_WEEK = "29-AUG-2022 - 04-SEP-2022";
    private static final String NONEXISTING_PERIOD = "29-AUG-2022 - 21-AUG-2022";

    private static final String EXISTING_DAY1 = "15-FEB-2021";
    private static final String EXISTING_DAY2 = "29-AUG-2022";
    private static final String EXISTING_DAY3 = "02-OCT-2022";

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
    private CommentRepository commentRepository;

    @Mock
    private PropertiesRepository propertiesRepository;

    @InjectMocks
    private EvidenceServiceImpl evidenceService;

    @Mock
    private EvidenceErrorServiceImpl evidenceErrorService;

    @Mock
    private CommentServiceImpl commentService;

    @Mock
    private PersonServiceImpl personService;

    @Mock
    private PropertiesServiceImpl propertiesService;

    private static DateTimeFormatter format = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());
    
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

        assertEquals(EXISTING_MONTH1_WEEK1, weeks1.get(WEEK_LIST_1));
        assertEquals(EXISTING_MONTH2_WEEK3, weeks2.get(WEEK_LIST_3));
        assertEquals(EXISTING_MONTH3_WEEK6, weeks3.get(WEEK_LIST_6));
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
        assertEquals(EXISTING_PERIOD_WEEK, evidenceService.getWeekForPeriod(EXISTING_PERIOD));
    }

    /**
     * Periodo incorrecto debería devolver error.
     */
    @Test
    public void nonexistingPeriodShouldReturnError() {
        assertThrows(IllegalArgumentException.class, () -> evidenceService.getWeekForPeriod(NONEXISTING_PERIOD));
    }

    /**
     * Si deleteComments es true, borrar EvidenceComment.
     */
    @Test
    public void ifDeleteCommentsEmptyEvidenceComment() {
        evidenceService.clearReport(true, true);
        assertTrue(commentService.getComments().isEmpty());
    }

    /**
     * Comprobar que datos de evidencias son borrados al comenzar el proceso.
     */
    @Test
    public void beginningProcessShouldEmptyEvidence() {
        evidenceService.clearReport(false, true);
        assertTrue(evidenceService.getEvidences().isEmpty());
        assertTrue(evidenceErrorService.getEvidenceErrors().isEmpty());
        assertTrue(propertiesService.getProperties().isEmpty());
    }

}
