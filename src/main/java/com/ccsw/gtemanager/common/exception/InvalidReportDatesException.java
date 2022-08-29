package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * InvalidReportDatesException: error con código 400 BAD REQUEST, para envíos
 * que contienen ficheros de informe válidos, pero con fechas de periodo o
 * ejecución incorrectas.
 */
public class InvalidReportDatesException extends ResponseStatusException {

    /**
     * Constructor: crear ResponseStatusException con código 400 y mensaje de error.
     */
    public InvalidReportDatesException() {
        super(HttpStatus.BAD_REQUEST, "El informe no contiene fechas de periodo y/o ejecución válidas (B2, C2, B10).");
    }

}
