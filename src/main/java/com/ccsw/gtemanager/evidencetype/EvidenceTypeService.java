package com.ccsw.gtemanager.evidencetype;

import java.util.List;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;

/**
 * EvidenceTypeService: servicio de datos de tipos de evidencia.
 */
public interface EvidenceTypeService {

	/**
	 * Obtener un listado de tipos de evidencia.
	 * 
	 * @return Listado de EvidenceType
	 */
	List<EvidenceType> getAll();

	/**
	 * Obtener tipo de evidencia dado código del tipo, ignorando mayúsculas y
	 * minúsculas.
	 * 
	 * @param code Código de EvidenceType a buscar
	 * @return EvidenceType encontrado
	 */
	EvidenceType getByCode(String code);

}
