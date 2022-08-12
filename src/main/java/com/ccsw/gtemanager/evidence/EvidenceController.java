package com.ccsw.gtemanager.evidence;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.evidence.model.FormDataDto;

/**
 * EvidenceController: Controlador REST para interacción con datos. Contiene
 * métodos de acceso a servicio de datos, asociados a Requests HTTP.
 * 
 * @author cavire
 *
 */
@RequestMapping(value = "/evidence")
@RestController
public class EvidenceController {

	/**
	 * POST: Recibe elemento con archivo de evidencias (formato .xls o .xlsx) y
	 * booleano de borrado de comentarios.
	 * 
	 * @param upload Elemento FormDataDto recibido desde el frontend
	 */
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void upload(@ModelAttribute FormDataDto upload) {

		System.out
				.println("file: " + upload.getFile().getOriginalFilename() + " : " + upload.getFile().getContentType());
		System.out.println("deleteComments: " + upload.isDeleteComments());

		// TODO Implementar en GM-06

	}
}
