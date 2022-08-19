package com.ccsw.gtemanager.evidence;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.person.model.Person;

/**
 * EvidenceRepository: repositorio de datos de evidencias.
 */
@Repository
public interface EvidenceRepository extends CrudRepository<Evidence, Long> {

	/**
	 * Obtener listado de evidencias dada una persona
	 * 
	 * @param personId persona (Person) cuyo registro de evidencias se desea
	 *                 encontrar
	 * @return Listado de evidencias obtenido
	 */
	List<Evidence> findByPersonId(Person personId);

}
