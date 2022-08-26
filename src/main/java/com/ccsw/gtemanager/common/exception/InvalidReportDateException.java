package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidReportDateException extends ResponseStatusException {

	public InvalidReportDateException() {
		super(HttpStatus.BAD_REQUEST, "El informe no contiene fechas de periodo y/o ejecución válidas (B2, C2, B10).");
	}

}
