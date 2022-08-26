package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidFileException extends ResponseStatusException {

	public InvalidFileException() {
		super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "No se ha recibido un fichero válido.");
	}

}
