package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.evidence.model.Evidence;

/**
 * EvidenceService: servicio de datos de evidencias.
 */
public interface EvidenceService {
	
	List<Evidence> findOrderedByGeography(Long idGeography);

    /**
     * Obtener un listado de todas las evidencias.
     * 
     * @return Listado de Evidences
     */
    List<Evidence> getEvidences();

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
