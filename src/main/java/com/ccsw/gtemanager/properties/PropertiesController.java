package com.ccsw.gtemanager.properties;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.properties.model.Properties;
import com.ccsw.gtemanager.properties.model.PropertiesDto;

@RequestMapping(value = "/properties")
@RestController
public class PropertiesController {

	@Autowired
	private PropertiesService propertiesService;
	
	@Autowired
	private BeanMapper beanMapper;
	
	/**
	 * GET: Devuelve el listado de propiedades
	 */
	 @RequestMapping(path = "", method = RequestMethod.GET)
	 public List<PropertiesDto> findAll() {
		 
		 List<Properties> properties = propertiesService.getProperties();
		 
		 return beanMapper.mapList(properties, PropertiesDto.class);
	 }
}
