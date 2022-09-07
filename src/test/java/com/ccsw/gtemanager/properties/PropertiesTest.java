package com.ccsw.gtemanager.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * TODO DOCS
 *
 */
@ExtendWith(MockitoExtension.class)
public class PropertiesTest {

    private static final String EXISTING_RUNDATE = "July 27, 2022 08:30 AM";

    @Mock
    private PropertiesRepository propertiesRepository;

    @InjectMocks
    private PropertiesServiceImpl propertiesService;

    /**
     * TODO DOCS
     * 
     */
    @Test
    public void getPropertyShouldReturnProperty() {
        assertEquals(EXISTING_RUNDATE, propertiesService.getProperty("LOAD_DATE"));
    }

    /**
     * TODO DOCS
     * 
     */
    @Test
    public void getInvalidPropertyShouldReturnError() {
        assertThrows(IllegalArgumentException.class, () -> propertiesService.getProperty("LOAD_DATES"));
    }

    /**
     * TODO DOCS
     * 
     */
    @Test
    public void getWeeksShouldReturnWeeksForPeriod() {
        List<String> weeks = propertiesService.getWeeks();
        assertEquals(5, weeks.size());
        assertEquals("", weeks.get(0));
        assertEquals("", weeks.get(1));
        assertEquals("", weeks.get(2));
        assertEquals("", weeks.get(3));
        assertEquals("", weeks.get(4));
        assertThrows(IndexOutOfBoundsException.class, () -> weeks.get(5));
    }

}
