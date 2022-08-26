package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnreadableReportException extends ResponseStatusException {

	public UnreadableReportException() {
		super(HttpStatus.BAD_REQUEST,
				"Se ha producido un error leyendo el archivo. Compruebe la validez de los datos y que no se encuentra encriptado.");
	}

}
