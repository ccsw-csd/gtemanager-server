package com.ccsw.gtemanager.center;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * CenterTest: colección de tests unitarios que prueban funcionalidades de la
 * aplicación backend.
 */
@ExtendWith(MockitoExtension.class)
public class CenterTest {

    @Mock
    private CenterRepository centerRepository;

    @InjectMocks
    private CenterServiceImpl centerService;

    /**
     * TODO DOCS
     */
    @Test
    public void getCentersShouldReturnAllCenters() {
        assertEquals(4, centerService.findAll().size());
    }

}
