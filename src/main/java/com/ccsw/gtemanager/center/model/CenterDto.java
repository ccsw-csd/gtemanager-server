package com.ccsw.gtemanager.center.model;

/**
 * CenterDto: DTO para la gestión de datos de centros. Contiene atributo para el
 * nombre, además de getters y setters.
 */
public class CenterDto {

    private Long id;
    private String name;

    /**
     * Obtener ID de Center
     * 
     * @return ID en formato Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtener nombre de Center
     * 
     * @return nombre en formato String
     */
    public String getName() {
        return name;
    }

    /**
     * Almacenar ID de Center
     * 
     * @param id ID de Center (Long)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Almacenar nombre de Center
     * 
     * @param name nombre de Center (String)
     */
    public void setName(String name) {
        this.name = name;
    }

}
