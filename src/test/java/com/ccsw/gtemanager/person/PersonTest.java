package com.ccsw.gtemanager.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.ccsw.gtemanager.person.model.Person;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    private static final String EXISTING_SAGA = "00A2";
    private static final String EXISTING_UNPARSED_SAGA = "S_000A2";
    private static final String NONEXISTING_UNPARSED_SAGA = "FD242";

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Mock
    private PersonRepository personRepository;

    @SuppressWarnings("unchecked")
    @Test
    public void findPersonByFilterShouldReturnPersons() {

        String filter = "oscar";

        List<Person> persons = new ArrayList<>();
        persons.add(mock(Person.class));
        persons.add(mock(Person.class));
        persons.add(mock(Person.class));
        persons.add(mock(Person.class));

        Pageable pageable = PageRequest.of(0, 15);
        Page<Person> page = new PageImpl<>(persons, pageable, 15);

        when(personRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        List<Person> personList = personServiceImpl.findAllPersonsFromFilters(filter);
        assertNotNull(personList);
        assertEquals(4, personList.size());
    }

    @Test
    public void parseValidSagaShouldReturnSaga() {
        assertEquals(EXISTING_SAGA, personServiceImpl.parseSaga(EXISTING_UNPARSED_SAGA));
    }

    /**
     * Un código saga inválido debe resultar en error de lectura.
     */
    @Test
    public void parseInvalidSagaShouldReturnError() {
        assertThrows(IllegalArgumentException.class, () -> personServiceImpl.parseSaga(NONEXISTING_UNPARSED_SAGA));
    }

}
