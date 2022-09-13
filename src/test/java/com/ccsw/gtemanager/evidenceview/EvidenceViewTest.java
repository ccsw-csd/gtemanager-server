package com.ccsw.gtemanager.evidenceview;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ccsw.gtemanager.evidenceview.model.EvidenceView;

@ExtendWith(MockitoExtension.class)
public class EvidenceViewTest {

    public static final Integer TOTAL_EVIDENCE = 1;

    @Mock
    private EvidenceViewRepository evidenceViewRepository;

    @InjectMocks
    private EvidenceViewServiceImpl evidenceViewServiceImpl;

    @Test
    public void findAllShouldReturnEvidenceList() {

        List<EvidenceView> list = new ArrayList<>();
        list.add(mock(EvidenceView.class));

        when(evidenceViewRepository.findAll(any(), any())).thenReturn(list);

        List<EvidenceView> evidences = evidenceViewServiceImpl.findByGeography(null);

        assertNotNull(evidences);
        assertEquals(TOTAL_EVIDENCE, evidences.size());
    }
}
