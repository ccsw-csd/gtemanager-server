package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * EntityNotFoundException: error con código 404 NOT FOUND, para peticiones de
 * un recurso o elemento que no existe o es inaccesible.
 */
public class EntityNotFoundException extends ResponseStatusException {

    /**
     * Constructor: crear ResponseStatusException con código 404 y mensaje de error.
     */
    public EntityNotFoundException() {
        super(HttpStatus.NOT_FOUND, "No se ha podido encontrar el elemento.");
    }

}
