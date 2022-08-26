package com.ccsw.gtemanager.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * PersonTest: colección de tests unitarios que prueban funcionalidades de la
 * aplicación backend.
 */
@ExtendWith(MockitoExtension.class)
public class PersonTest {

	private static final String EXISTING_SAGA = "00A2";
	private static final String EXISTING_UNPARSED_SAGA = "S_000A2";
	private static final String NONEXISTING_UNPARSED_SAGA = "FD242";

	@InjectMocks
	private PersonServiceImpl personService;

	/**
	 * Un código saga válido debe poder procesarse.
	 */
	@Test
	public void parseValidSagaShouldReturnSaga() {
		assertEquals(EXISTING_SAGA, personService.parseSaga(EXISTING_UNPARSED_SAGA));
	}

	/**
	 * Un código saga inválido debe resultar en error de lectura.
	 */
	@Test
	public void parseInvalidSagaShouldReturnError() {
		assertThrows(IllegalArgumentException.class, () -> personService.parseSaga(NONEXISTING_UNPARSED_SAGA));
	}
}
