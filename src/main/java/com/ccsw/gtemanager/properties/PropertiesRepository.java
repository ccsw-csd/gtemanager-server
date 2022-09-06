package com.ccsw.gtemanager.properties;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.properties.model.Properties;

/**
 * PropertiesRepository: repositorio de datos de propiedades.
 */
@Repository
public interface PropertiesRepository extends JpaRepository<Properties, Long>{

	List<Properties> findAll();

    /**
     * Obtener propiedad buscando por clave.
     * 
     * @param key clave en formato String para buscar
     * @return objeto Properties con propiedad almacenada
     */
    Properties findByKey(String key);
}
