package com.ccsw.gtemanager.properties;

import java.time.DateTimeException;
import java.time.LocalDateTime;
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
     * Obtener y procesar listado de semanas almacenadas en BD como String.
     * 
     * @return Listado de String con valores de las semanas en el periodo
     */
    List<String> getWeeks();

    /**
     * Obtener propiedad buscando por la clave especificada.
     * 
     * @param key Clave por la que buscar en BD
     * @return Properties hallado, NULL si no hallado
     */
    Properties getProperty(String key);

    /**
     * Leer y almacenar propiedades de la hoja de cálculo recibida. Deducir fecha de
     * carga, nombre de usuario, semanas dentro del periodo, y número de semanas.
     * Almacenar como objetos Properties.
     * 
     * @param runDate Fecha de ejecución de informe
     * @param weeks   Listado de semanas a procesar
     * @return List de Properties procesadas para su almaecenamiento en BD
     * @throws DateTimeException Existen fechas no admisibles
     */
    List<Properties> parseProperties(LocalDateTime runDate, List<String> weeks) throws DateTimeException;

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
