package com.ccsw.gtemanager.evidenceerror;

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

import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;

@ExtendWith(MockitoExtension.class)
public class EvidenceErrorTest {
    public static final Integer TOTAL_ERROR = 3;

    @Mock
    private EvidenceErrorRepository errorRepository;

    @InjectMocks
    private EvidenceErrorServiceImpl errorServiceImpl;

    @Test
    public void findAllShouldReturnErrorList() {

        List<EvidenceError> list = new ArrayList<>();
        list.add(mock(EvidenceError.class));
        list.add(mock(EvidenceError.class));
        list.add(mock(EvidenceError.class));

        when(errorRepository.findAll()).thenReturn(list);

        List<EvidenceError> errors = errorServiceImpl.getEvidenceErrors();

        assertNotNull(errors);
        assertEquals(TOTAL_ERROR, errors.size());
    }
}
