package com.ccsw.gtemanager.evidencemanager;

import com.ccsw.gtemanager.evidence.model.FormDataDto;
import org.springframework.web.server.ResponseStatusException;

public interface EvidenceManagerService {

    /**
     * Leer y procesar un archivo de hoja de cálculo para obtener y almacenar los gestores.
     *
     * @param upload Elemento de subida de archivo
     * @return true si se han guardado todos los datos correctamente, false si se han guardado con errores
     * @throws ResponseStatusException Hay errores a la hora de procesar el envío recibido
     */
    Boolean uploadEvidenceManager(FormDataDto upload) throws ResponseStatusException;

    void clear();
}
