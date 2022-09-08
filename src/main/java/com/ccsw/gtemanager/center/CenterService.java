package com.ccsw.gtemanager.center;

import java.util.List;

import com.ccsw.gtemanager.center.model.Center;

/**
 * CenterService: servicio de datos de centros.
 *
 */
public interface CenterService {

    /**
     * Obtener listado de todos los centros almacenados en la base de datos.
     * 
     * @return Listado de Center
     */
    List<Center> findAll();

}
