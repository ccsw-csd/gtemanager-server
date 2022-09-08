package com.ccsw.gtemanager.common.criteria;

/**
 * SearchCriteria: objeto para definición de criterio de búsqueda en
 * especificaciones.
 */
public class SearchCriteria {

    private String key;
    private String operation;
    private Object value;

    public SearchCriteria(String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
    }

    /**
     * Obtener clave del criterio de búsqueda
     * 
     * @return Clave en formato String
     */
    public String getKey() {
        return key;
    }

    /**
     * Obtener tipo de operación del criterio de búsqueda
     * 
     * @return Operación en formato String
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Obtener valor del criterio de búsqueda
     * 
     * @return Valor en formato String
     */
    public Object getValue() {
        return value;
    }

    /**
     * Almacenar clave del criterio de búsqueda
     * 
     * @param key Clave (String)
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Almacenar tipo de operación del criterio de búsqueda
     * 
     * @param operation Operación (String)
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Almacenar valor del criterio de búsqueda
     * 
     * @param value Valor (String)
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
