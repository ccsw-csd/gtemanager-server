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
     * Obtener propiedad buscando por la clave especificada.
     * 
     * @param key Clave por la que buscar en BD
     * @return Properties hallado, NULL si no hallado
     */
    Properties getProperty(String key);

    /**
     * Obtener y procesar listado de semanas almacenadas en BD como String.
     * 
     * @return Listado de String con valores de las semanas en el periodo
     */
    List<String> getWeeks();

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
