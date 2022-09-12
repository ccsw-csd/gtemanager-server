package com.ccsw.gtemanager.center;

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

import com.ccsw.gtemanager.center.model.Center;

@ExtendWith(MockitoExtension.class)
public class CenterTest {
	
	public static final Integer TOTAL_CENTER = 1;
	
	@Mock
	private CenterRepository centerRepository;
	
	@InjectMocks
	private CenterServiceImpl centerServiceImpl;
	
	@Test
	public void findAllShouldReturnCenterList() {
		
		List<Center> list = new ArrayList<>();
		list.add(mock(Center.class));
		
		when(centerRepository.findAll()).thenReturn(list);
		
		List<Center> centers = centerServiceImpl.findAll();
		
		assertNotNull(centers);
		assertEquals(TOTAL_CENTER, centers.size());
	}
}
