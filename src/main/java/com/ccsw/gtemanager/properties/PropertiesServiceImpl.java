package com.ccsw.gtemanager.properties;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.properties.model.Properties;

/**
 * PropertiesServiceImpl: clase de implementación de PropertiesService
 */
@Service
@Transactional
public class PropertiesServiceImpl implements PropertiesService {

    @Autowired
    private PropertiesRepository propertiesRepository;

    @Override
    public List<Properties> getProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public Properties getProperty(String key) {
        return propertiesRepository.findByKey(key);
    }

    @Override
    public void saveAll(List<Properties> propertiesList) {
        propertiesRepository.saveAll(propertiesList);
    }

    @Override
    public void clear() {
        propertiesRepository.deleteAllInBatch();
    }

}
