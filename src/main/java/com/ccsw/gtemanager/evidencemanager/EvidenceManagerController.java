package com.ccsw.gtemanager.evidencemanager;

import com.ccsw.gtemanager.common.exception.GlobalExceptionHandler;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping(value = "/evidence-manager")
@RestController
public class EvidenceManagerController {

	@Autowired
	private EvidenceManagerService evidenceManagerService;

	/**
	 * POST: Recibe elemento con archivo de evidencias (formato .xls o .xlsx)
	 *
	 * La gestión de errores y excepciones se realiza en {@link GlobalExceptionHandler}.
	 *
	 * @param upload Elemento FormDataDto recibido desde el frontend
	 * @return NULL si se ha procesado correctamente, mensaje si se ha procesado correctamente, pero con errores.
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<String> uploadEvidenceManager(@ModelAttribute FormDataDto upload) {

		if (evidenceManagerService.uploadEvidenceManager(upload)){
			return ResponseEntity.status(HttpStatus.OK).body(null);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body("Se ha guardado el informe correctamente con algunos errores de gestores.");
		}
	}

}
