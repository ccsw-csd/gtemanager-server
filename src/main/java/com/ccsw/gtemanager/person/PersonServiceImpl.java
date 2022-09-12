package com.ccsw.gtemanager.person;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.personsagatranscode.PersonSagaTranscodeService;
import com.ccsw.gtemanager.personsagatranscode.model.PersonSagaTranscode;

/**
 * PersonServiceImpl: clase de implementación de PersonService.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {
	
    private static final String SAGA_SEPARATOR = "_";

    @Autowired
    private PersonSagaTranscodeService personSagaTranscodeService;

	@Autowired
	PersonRepository personRepository;
	
	@Override
	public Person findById(Long id) throws EntityNotFoundException {
		return this.personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
	}

    @Override
    public List<Person> getPeople() {
        List<PersonSagaTranscode> personTranscodes = personSagaTranscodeService.getPersonSagaTranscodes();
        List<Person> people = personRepository.findAll();
        for (PersonSagaTranscode personSagaTranscode : personTranscodes) {
            Person person = personSagaTranscode.getPersonId();
            person.setSaga(personSagaTranscode.getSaga());
            try {
                people.set(people.indexOf(person), person);
            } catch (IndexOutOfBoundsException e) {
                people.add(person);
            }
        }
        return people;
    }

    @Override
    public String parseSaga(String saga) throws IllegalArgumentException {
        try {
            return String.valueOf(Long.parseLong(saga.split(SAGA_SEPARATOR)[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Código Saga introducido no es válido.");
        } catch (NumberFormatException e) {
            return saga.substring(saga.length() - 4);
        }
    }
}