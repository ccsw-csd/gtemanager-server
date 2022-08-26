package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleException(ResponseStatusException e) {
		return new ResponseEntity<>(e.getReason(), e.getStatus());
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleInternalServerError(Exception e) {
		return new ResponseEntity<>(
				"Se ha producido un error interno. Por favor, póngase en contacto con un administrador. Disculpe las molestias.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
