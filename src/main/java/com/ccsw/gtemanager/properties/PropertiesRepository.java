package com.ccsw.gtemanager.properties;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.properties.model.Properties;

public interface PropertiesRepository extends CrudRepository<Properties, Long>{

	List<Properties> findAll();
}
