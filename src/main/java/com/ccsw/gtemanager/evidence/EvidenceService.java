package com.ccsw.gtemanager.evidence;

import java.util.List;
import java.util.Map;

import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.person.model.Person;

/**
 * EvidenceService: servicio de datos de evidencias.
 */
public interface EvidenceService {

    /**
     * Obtener listado de evidencias según el centro referenciado por la persona
     * asociada a la evidencia.
     * 
     * @param idGeography ID del centro por la que filtrar
     * @return List de evidencias filtradas y ordenadas por centro
     */
    List<Evidence> findByGeography(Long idGeography);

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
     * @param evidences Map de Person y Evidence en el que buscar
     * @param person    Person por el que buscar
     * @return Evidence hallado o Evidence nuevo en caso de no hallarse
     */
    Evidence getEvidenceForPerson(Map<Person, Evidence> evidences, Person person);

    Map<String, Person> createSagaPersonMap();

    /**
     * Actualiza un evidencia para una persona determinada indicando que se le ha
     * enviado la notificación por email.
     * 
     * @param id de Person
     * @return Evidence hallado
     */
    void setEmailNotificationSentForPersonId(Long id);

    /**
     * Obtener tipo de evidencia registrado en cada semana, en un mapa asociado a
     * dicha semana.
     * 
     * @param evidence Evidencia en la que buscar
     * @param weeks    Listado de semanas a comparar
     * @return Map con tipos asociados a las semanas registradas
     */
    Map<String, EvidenceType> getTypesForEvidence(Evidence evidence, List<String> weeks);

    /**
     * Leer y procesar un archivo de hoja de cálculo para obtener y almacenar
     * evidencias.
     * 
     * @param upload Elemento de subida de archivo con boolean deleteComments
     * @return true si se han guardado todos los datos correctamente, false si se
     *         han guardado con errores
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

    void mapPerson(Long personId, String saga);

}
