package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * AlreadyExistsException: error con código 409 CONFLICT, para peticiones que
 * causan un conflicto al ya existir un elemento recibido.
 */
public class AlreadyExistsException extends ResponseStatusException {

	/**
	 * Constructor: crear ResponseStatusException con código 409 y mensaje de error.
	 */
	public AlreadyExistsException() {
		super(HttpStatus.CONFLICT, "Already exists");
	}

}