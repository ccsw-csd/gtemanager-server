package com.ccsw.gtemanager.evidence;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.gtemanager.evidence.model.UploadDto;

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
	public ResponseEntity<String> uploadEvidence(@ModelAttribute UploadDto upload) {
		if (upload.getFile() == null || !StringUtils.hasText(upload.getFile().getContentType()))
			return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
					.body("No se ha recibido un fichero válido.");

		if (!upload.getFile().getContentType().equals("application/vnd.ms-excel") && !upload.getFile().getContentType()
				.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body("No se ha recibido un fichero de hoja de cálculo.");

		System.out
				.println("file: " + upload.getFile().getOriginalFilename() + " : " + upload.getFile().getContentType());
		System.out.println("deleteComments: " + upload.isDeleteComments());

		return ResponseEntity.status(HttpStatus.OK).body(null);

	}
}
