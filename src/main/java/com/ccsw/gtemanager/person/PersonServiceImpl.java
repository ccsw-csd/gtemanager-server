package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.gtemanager.common.criteria.TernarySearchCriteria;
import com.ccsw.gtemanager.config.mapper.BeanMapper;
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
    PersonRepository personRepository;

    @Autowired
    BeanMapper beanMapper;

    @Override
    public List<Person> findAllPersonsFromFilters(String filter) {

        PersonSpecification username = new PersonSpecification(
                new TernarySearchCriteria("username", null, null, ":", filter));

        PersonSpecification firstnameLastname = new PersonSpecification(
                new TernarySearchCriteria("name", "lastName", null, "concat :", filter));

        PersonSpecification lastnameFirstname = new PersonSpecification(
                new TernarySearchCriteria("lastName", "name", null, "concat :", filter));

        Specification<Person> specification = Specification.where(username).or(lastnameFirstname.or(firstnameLastname));

        Page<Person> personsLike = personRepository.findAll(specification, PageRequest.of(0, 15));

        return personsLike.getContent();
    }

    private static final String SAGA_SEPARATOR = "_";

    @Autowired
    private PersonSagaTranscodeService personSagaTranscodeService;

    @Override
    public List<Person> getPeople() {
        List<PersonSagaTranscode> personTranscodes = personSagaTranscodeService.getPersonSagaTranscodes();
        List<Person> people = (List<Person>) personRepository.findAll();
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
