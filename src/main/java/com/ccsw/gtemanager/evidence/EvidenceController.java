package com.ccsw.gtemanager.evidence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.common.exception.GlobalExceptionHandler;
import com.ccsw.gtemanager.evidence.model.FormDataDto;

/**
 * EvidenceController: Controlador REST para interacción con datos. Contiene
 * métodos de acceso a servicio de datos, asociados a Requests HTTP.
 */
@RequestMapping(value = "/evidence")
@RestController
public class EvidenceController {

    @Autowired
    private EvidenceService evidenceService;

    /**
     * POST: Recibe elemento con archivo de evidencias (formato .xls o .xlsx) y
     * booleano de borrado de comentarios.
     * 
     * La gestión de errores y excepciones se realiza en
     * {@link GlobalExceptionHandler}.
     * 
     * @param upload Elemento FormDataDto recibido desde el frontend
     * @return NULL si se ha procesado correctamente, mensaje si se ha procesado
     *         correctamente, pero con errores.
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<String> uploadEvidence(@ModelAttribute FormDataDto upload) {
        if (evidenceService.uploadEvidence(upload))
            return ResponseEntity.status(HttpStatus.OK).body(null);
        else
            return ResponseEntity.status(HttpStatus.OK).body("\"Se ha guardado el informe correctamente con algunos errores de evidencias.\"");
    }

    @RequestMapping(path = "/mapPerson/{personId}/{email}", method = RequestMethod.PUT)
    public void mapPerson(@PathVariable Long personId, @PathVariable String email) {

        evidenceService.mapPerson(personId, email);
    }
}
