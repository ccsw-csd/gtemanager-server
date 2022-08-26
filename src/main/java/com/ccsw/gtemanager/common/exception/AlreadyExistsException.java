package com.ccsw.gtemanager.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlreadyExistsException extends ResponseStatusException {

	public AlreadyExistsException() {
		super(HttpStatus.CONFLICT, "Already exists");
	}

}