package com.ccsw.gtemanager.evidence;

import java.io.IOException;
import java.util.List;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.properties.model.Properties;

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
	 * Obtener un listado de todos los errores de evidencias.
	 * 
	 * @return Listado de EvidenceErrors
	 */
	List<EvidenceError> getEvidenceErrors();

	/**
	 * Obtener un listado de todos los comentarios de evidencias.
	 * 
	 * @return Listado de EvidenceComments
	 */
	List<EvidenceComment> getEvidenceComments();

	/**
	 * Obtener un listado de propiedades del informe.
	 * 
	 * @return Listado de Properties
	 */
	List<Properties> getProperties();

	/**
	 * Obtener un listado de tipos de evidencia.
	 * 
	 * @return Listado de EvidenceType
	 */
	List<EvidenceType> getEvidenceTypes();

	/**
	 * Obtener tipo de evidencia dado código del tipo, ignorando mayúsculas y
	 * minúsculas.
	 * 
	 * @param type Código de EvidenceType a buscar
	 * @return EvidenceType encontrado
	 */
	EvidenceType findEvidenceType(String type);

	/**
	 * Obtener evidencia concreta dada una persona.
	 * 
	 * @param person Person implicada en la evidencia
	 * @return Evidence encontrado
	 */
	Evidence findEvidencePerPerson(Person person);

	/**
	 * Obtener persona dado un código de saga.
	 * 
	 * @param saga Código saga por el que buscar
	 * @return Person encontrado
	 */
	Person findPersonBySaga(String saga);

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
	 * Limpiar datos de evidencias, comentarios, errores, y parámetros.
	 * 
	 * @param deleteComments Controlar si se desea borrar comentarios
	 */
	void clearEvidenceData(boolean deleteComments);

}
