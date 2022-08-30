package com.ccsw.gtemanager.properties;

import java.util.List;

import com.ccsw.gtemanager.properties.model.Properties;

/**
 * PropertiesService: servicio de datos de propiedades.
 */
public interface PropertiesService {

    /**
     * Obtener un listado de propiedades del informe.
     * 
     * @return Listado de Properties
     */
    List<Properties> getProperties();

    /**
     * Guardar todos los registros de Properties porporcionados.
     * 
     * @param propertiesList Listado de Properties a almacenar
     */
    void saveAll(List<Properties> propertiesList);

    /**
     * Eliminar todos los registros de Properties.
     */
    void clear();

}
