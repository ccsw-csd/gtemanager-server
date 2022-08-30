package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * UnprocessableEntityException: error con código 422 UNPROCESSABLE ENTITY, para
 * envíos que contienen ficheros válidos, pero con formato o contenido
 * incorrecto.
 */
public class UnprocessableEntityException extends ResponseStatusException {

    /**
     * Constructor: crear ResponseStatusException con código 422 y mensaje de error.
     */
    public UnprocessableEntityException() {
        super(HttpStatus.UNPROCESSABLE_ENTITY, "No se ha recibido un elemento con formato válido.");
    }

}
