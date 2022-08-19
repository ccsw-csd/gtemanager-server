package com.ccsw.gtemanager.properties;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.properties.model.Properties;

/**
 * PropertiesRepository: repositorio de datos de propiedades.
 */
@Repository
public interface PropertiesRepository extends CrudRepository<Properties, Long> {

	/**
	 * Obtener propiedad buscando por clave.
	 * 
	 * @param key clave en formato String para buscar
	 * @return objeto Properties con propiedad almacenada
	 */
	Properties findByKey(String key);

}
