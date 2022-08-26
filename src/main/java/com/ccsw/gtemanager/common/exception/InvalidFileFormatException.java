package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFileFormatException extends ResponseStatusException {

	public InvalidFileFormatException() {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "No se ha recibido un fichero con formato válido.");
	}

}
