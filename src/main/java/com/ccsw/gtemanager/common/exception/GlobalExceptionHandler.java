package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler: gestión de las excepciones en la ejecución para
 * repuestas a frontend.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Obtener estado HTTP y mensaje de la excepción para devolver respuesta a
	 * frontend.
	 * 
	 * @param e Excepción (ResponseStatusException) capturada
	 * @return Respuesta (ResponseEntity) para frontend
	 */
	@ExceptionHandler(ResponseStatusException.class)
	public ResponseEntity<String> handleException(ResponseStatusException e) {
		return new ResponseEntity<>(e.getReason(), e.getStatus());
	}

	/**
	 * Devolver respuesta 500 INTERNAL SERVER ERROR en excepciones genéricas a
	 * frontend.
	 * 
	 * @param e Excepción capturada
	 * @return Respuesta (ResponseEntity) para frontend
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleInternalServerError(Exception e) {
		return new ResponseEntity<>(
				"Se ha producido un error interno. Por favor, póngase en contacto con un administrador. Disculpe las molestias.",
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
