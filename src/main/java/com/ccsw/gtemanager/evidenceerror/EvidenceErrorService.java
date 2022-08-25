package com.ccsw.gtemanager.evidenceerror;

import java.util.List;

import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;

public interface EvidenceErrorService {

	/**
	 * Obtener un listado de todos los errores de evidencias.
	 * 
	 * @return Listado de EvidenceErrors
	 */
	List<EvidenceError> getAll();

	/**
	 * Guardar todos los registros de EvidenceError proporcionados.
	 * 
	 * @param errors Listado de EvidenceError a almacenar
	 */
	void saveAll(List<EvidenceError> errors);

	/**
	 * Eliminar todos los registros de EvidenceError.
	 */
	void clear();
}
