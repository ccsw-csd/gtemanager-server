package com.ccsw.gtemanager.evidenceerror;

import java.util.List;

import com.ccsw.gtemanager.evidenceerror.model.EvidenceErrorDto;

/**
 * EvidenceErrorService: servicio de datos de errores de evidencias.
 */
public interface EvidenceErrorService {

    /**
     * Obtener un listado de todos los errores de evidencias.
     * 
     * @return Listado de EvidenceErrorDto
     */
    List<EvidenceErrorDto> getAll();

    /**
     * Guardar todos los registros de EvidenceErrorDto proporcionados.
     * 
     * @param errors Listado de EvidenceErrorDto a almacenar
     */
    void saveAll(List<EvidenceErrorDto> errors);

    /**
     * Eliminar todos los registros de EvidenceError.
     */
    void clear();
}
