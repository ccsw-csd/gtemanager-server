package com.ccsw.gtemanager.personsagatranscode;

import java.util.List;

import com.ccsw.gtemanager.personsagatranscode.model.PersonSagaTranscode;

/**
 * PersonSagaTranscodeService: servicio de datos de personas con códigos saga
 * actualizados.
 */
public interface PersonSagaTranscodeService {

    /**
     * Obtener todos los registros de personas con nuevos códigos saga en la base de
     * datos.
     * 
     * @return Listado de PersonSagaTranscode
     */
    List<PersonSagaTranscode> getPersonSagaTranscodes();

}
