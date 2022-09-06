package com.ccsw.gtemanager.evidence;

import java.util.List;
import java.util.Map;

import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.person.model.Person;

/**
 * EvidenceService: servicio de datos de evidencias.
 */
public interface EvidenceService {

    /**
     * Obtener un listado de todas las evidencias.
     * 
     * @return Listado de Evidences
     */
    List<Evidence> getEvidences();

    /**
     * Obtener evidencia para una persona determinada. Se busca en el mapa de
     * evidencias en procesamiento, y se devuelve un Evidence vacío, con la persona
     * asociada, en caso de no encontrarse.
     * 
     * @param person Person por el que buscar
     * @return Evidence hallado o Evidence nuevo en caso de no hallarse
     */
    Evidence getEvidenceForPerson(Person person);

    /**
     * TODO DOCS
     * 
     * @param evidence
     * @return
     * @throws Exception
     */
    Map<String, String> getTypesForEvidence(Evidence evidence, List<String> weeks);

    /**
     * TODO DOCS
     * 
     * @param centerId
     * @return
     */
    List<Evidence> getEvidencesByCenter(Long centerId);

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
