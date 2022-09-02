package com.ccsw.gtemanager.evidencetype;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;

/**
 * EvidenceTypeRepository: repositorio de tipos de evidencia.
 */
@Repository
public interface EvidenceTypeRepository extends CrudRepository<EvidenceType, Long> {

    /**
     * Obtener listado de tipos de evidencia dado un código
     * 
     * @param code código (String) cuyo registro de tipos se desea encontrar
     * @return Listado de tipos obtenido
     */
    List<EvidenceType> findByCodeIgnoreCase(String code);

}
