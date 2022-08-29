package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * InvalidUploadException: error con código 415 UNSUPPORTED MEDIA TYPE, para
 * envíos que no contienen ficheros válidos o procesables.
 */
public class InvalidUploadException extends ResponseStatusException {

    /**
     * Constructor: crear ResponseStatusException con código 415 y mensaje de error.
     */
    public InvalidUploadException() {
        super(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "No se ha recibido un fichero válido.");
    }

}
