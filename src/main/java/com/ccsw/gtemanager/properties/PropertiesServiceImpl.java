package com.ccsw.gtemanager.properties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.properties.model.Properties;

@Service
public class PropertiesServiceImpl implements PropertiesService {

	@Autowired
	PropertiesRepository propertiesRepository;
	
	@Override
	public List<Properties> findAll() {
	
		return this.propertiesRepository.findAll();
	}
}
