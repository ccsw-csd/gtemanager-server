package com.ccsw.gtemanager.person;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.person.model.Person;

/**
 * PersonServiceImpl: clase de implementación de PersonService.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> getPeople() {
        return personRepository.findAll();
    }

    @Override
    public Person getById(Long id) {
        return personRepository.getById(id);
    }

    @Override
    public Person getBySaga(String saga) {
        List<Person> people = personRepository.findBySaga(saga);
        return people.size() == 1 ? people.get(0) : null;
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
