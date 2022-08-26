package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * UnreadableReportException: error con código 400 BAD REQUEST, para envíos que
 * contienen ficheros válidos, pero que no han podido leerse por fallos en los
 * datos o acceso al archivo.
 */
public class UnreadableReportException extends ResponseStatusException {

	/**
	 * Constructor: crear ResponseStatusException con código 400 y mensaje de error.
	 */
	public UnreadableReportException() {
		super(HttpStatus.BAD_REQUEST,
				"Se ha producido un error leyendo el archivo. Compruebe la validez de los datos y que no se encuentra encriptado.");
	}

}
