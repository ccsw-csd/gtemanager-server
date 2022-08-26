package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.FormDataDto;

/**
 * EvidenceService: servicio de datos de evidencias.
 */
public interface EvidenceService {

	/**
	 * Obtener un listado de todas las evidencias.
	 * 
	 * @return Listado de Evidences
	 */
	List<Evidence> getAll();

	/**
	 * Leer y procesar un archivo de hoja de cálculo para obtener y almacenar
	 * evidencias.
	 * 
	 * @param upload Elemento de subida de archivo con boolean deleteComments
	 * @return true si se han guardadon todos los datos correctamente, false si se
	 *         ha guardado con errores
	 * @throws ResponseStatusException Hay errores a la hora de procesar el envío
	 *                                 recibido
	 */
	boolean uploadEvidence(FormDataDto upload) throws ResponseStatusException;

	/**
	 * Guardar todos los registros de Evidence proporcionados.
	 * 
	 * @param evidences Listado de Evidence a almacenar
	 */
	void saveAll(List<Evidence> evidences);

	/**
	 * Eliminar todos los registros de Evidence.
	 */
	void clear();
}
