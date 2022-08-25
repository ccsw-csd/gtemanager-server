package com.ccsw.gtemanager.evidence;

import java.io.IOException;
import java.util.List;

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
	 * @return true si se ha guardado todo correctamente, false si se ha guardado
	 *         con errores
	 * @throws IllegalArgumentException Hay errores en el archivo que no permiten el
	 *                                  guardado de datos
	 * @throws IOException              No se ha podido leer el libro de hojas de
	 *                                  cálculo proporcionado
	 */
	boolean uploadEvidence(FormDataDto upload) throws IllegalArgumentException, IOException;

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
