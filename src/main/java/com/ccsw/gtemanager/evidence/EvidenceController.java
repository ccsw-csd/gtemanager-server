package com.ccsw.gtemanager.evidence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	 * 
	 * @param upload Elemento FormDataDto recibido desde el frontend
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public ResponseEntity<String> uploadEvidence(@ModelAttribute FormDataDto upload) {
		if (upload.getFile() == null || !StringUtils.hasText(upload.getFile().getContentType()))
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
					.body("\"No se ha recibido un fichero válido.\"");

		if (!upload.getFile().getContentType().equals("application/vnd.ms-excel") && !upload.getFile().getContentType()
				.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body("\"No se ha recibido un fichero de hoja de cálculo.\"");

		boolean ok;
		try {
			ok = evidenceService.uploadEvidence(upload);
		} catch (IllegalArgumentException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("\""+e.getLocalizedMessage()+"\"");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
					"\"Se ha producido un error interno. Por favor, póngase en contacto con un administrador. Disculpe las molestias. ["
							+ e + "]\"");
		}

		if (ok)
			return ResponseEntity.status(HttpStatus.OK).body(null);
		else
			return ResponseEntity.status(HttpStatus.OK)
					.body("\"Se ha guardado el informe correctamente con algunos errores de evidencias.\"");
	}
}
