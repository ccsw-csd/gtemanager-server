package com.ccsw.gtemanager.person;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.personsagatranscode.PersonSagaTranscodeService;
import com.ccsw.gtemanager.personsagatranscode.model.PersonSagaTranscode;

/**
 * PersonServiceImpl: clase de implementación de PersonService.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonSagaTranscodeService personSagaTranscodeService;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getPeople() {
        List<PersonSagaTranscode> personTranscodes = personSagaTranscodeService.getAll();
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
    public Person getById(Long id) {
        return personRepository.getById(id);
    }

    @Override
    public String parseSaga(String saga) throws IllegalArgumentException {
        try {
            saga = saga.split("_")[1];
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Código Saga introducido no es válido.");
        }
        try {
            return String.valueOf(Long.parseLong(saga));
        } catch (NumberFormatException ex) {
            return saga.substring(saga.length() - 4);
        }
    }

}
