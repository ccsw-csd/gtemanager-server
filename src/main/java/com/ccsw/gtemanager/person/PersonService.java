package com.ccsw.gtemanager.person;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.model.Person;

public interface PersonService {
	
	Person findById(Long id) throws EntityNotFoundException;
}
