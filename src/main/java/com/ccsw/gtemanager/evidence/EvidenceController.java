package com.ccsw.gtemanager.evidence;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.common.exception.InvalidFileException;
import com.ccsw.gtemanager.common.exception.InvalidFileFormatException;
import com.ccsw.gtemanager.common.exception.InvalidReportDateException;
import com.ccsw.gtemanager.common.exception.UnreadableReportException;
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
	 * Se devuelve 200 OK si se ha procesado sin errores, 200 y un mensaje si
	 * existen errores, 400 BAD REQUEST si hay datos incorrectos, 415 UNSUPPORTED
	 * MEDIA TYPE o 422 UNPROCESSABLE ENTITY si el elemento recibido no es
	 * procesable, y 500 INTERNAL SERVER ERROR si se ha producido una excepción
	 * inesperada.
	 * TODO DOCS
	 * @param upload Elemento FormDataDto recibido desde el frontend
	 * @throws IOException 
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<String> uploadEvidence(@ModelAttribute FormDataDto upload) throws IOException {
		if (evidenceService.uploadEvidence(upload))
			return ResponseEntity.status(HttpStatus.OK).body(null);
		else
			return ResponseEntity.status(HttpStatus.OK)
					.body("\"Se ha guardado el informe correctamente con algunos errores de evidencias.\"");
	}
}
