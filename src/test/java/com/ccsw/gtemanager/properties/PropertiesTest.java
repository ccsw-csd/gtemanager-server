package com.ccsw.gtemanager.properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.gtemanager.properties.model.Properties;

@ExtendWith(MockitoExtension.class)
public class PropertiesTest {

    public static final Integer TOTAL_PROPERTIES = 2;

    @Mock
    private PropertiesRepository propertiesRepository;

    @InjectMocks
    private PropertiesServiceImpl propertiesServiceImpl;

    @Test
    public void findAllShouldReturnEvidenceList() {

        List<Properties> list = new ArrayList<>();
        list.add(mock(Properties.class));
        list.add(mock(Properties.class));

        when(propertiesRepository.findAll()).thenReturn(list);

        List<Properties> properties = propertiesServiceImpl.getProperties();

        assertNotNull(properties);
        assertEquals(TOTAL_PROPERTIES, properties.size());
    }
}
