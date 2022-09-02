package com.ccsw.gtemanager.personsagatranscode;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.personsagatranscode.model.PersonSagaTranscode;

/**
 * PersonSagaTranscodeRepository: repositorio de datos de personas con códigos
 * saga modificados.
 */
@Repository
public interface PersonSagaTranscodeRepository extends JpaRepository<PersonSagaTranscode, Long> {

}
