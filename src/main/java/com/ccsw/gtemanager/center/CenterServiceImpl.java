package com.ccsw.gtemanager.center;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.center.model.Center;

@Service
public class CenterServiceImpl implements CenterService {

	@Autowired
	CenterRepository centerRepository;
	
	@Override
	public List<Center> findAll() {
		return this.centerRepository.findAll();
	}
}
