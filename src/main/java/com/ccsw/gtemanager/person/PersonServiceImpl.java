package com.ccsw.gtemanager.person;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.common.criteria.TernarySearchCriteria;
import com.ccsw.gtemanager.common.exception.EntityNotFoundException;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.person.model.PersonSpecification;

/**
 * PersonServiceImpl: clase de implementación de PersonService.
 */
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private static final String SAGA_SEPARATOR = "_";

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Person findById(Long id) throws EntityNotFoundException {
        return this.personRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Person> getPeople() {
        return personRepository.findAll();
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

    @Override
    public Person findByEmail(String email) {
        return this.personRepository.findByEmail(email);
    }

    @Override
    public List<Person> findFirst15Filter(String filter) {

        PersonSpecification active = new PersonSpecification(new TernarySearchCriteria("active", null, null, ":", true));

        PersonSpecification usernameNameLastname = new PersonSpecification(new TernarySearchCriteria("username", "name", "lastName", "concat concat :", filter));

        Specification<Person> specification = Specification.where(active).and(usernameNameLastname);

        return this.personRepository.findAll(specification, PageRequest.of(0, 15)).getContent();
    }

    @Override
    public Person getByNameLastName(String name, String lastName) {

        List<Person> persons = personRepository.findByNameAndLastName(name, lastName);

        if (persons != null && persons.size() > 0)
            return persons.get(0);

        return null;
    }

}