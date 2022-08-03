package com.ccsw.gtemanager.evidence;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.evidence.model.UploadDTO;

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
	 * PUT: Recibe elemento con archivo de evidencias (formato .xls o .xlsx) y
	 * booleano de borrado de comentarios.
	 * 
	 * @param upload Elemento UploadDTO recibido desde el frontend
	 */
	@RequestMapping(path = "", method = RequestMethod.PUT)
	public void upload(@ModelAttribute UploadDTO upload) {

		System.out.println(upload.getFile().getOriginalFilename() + "..." + upload.getFile().getContentType());
		System.out.println(upload.isDeleteComments());

		// TODO Implementar en GM-06

	}
}
