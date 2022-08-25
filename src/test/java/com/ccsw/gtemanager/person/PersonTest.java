package com.ccsw.gtemanager.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import com.ccsw.gtemanager.person.model.PersonEntity;

@ExtendWith(MockitoExtension.class)
public class PersonTest {

    @InjectMocks
    private PersonServiceImpl personServiceImpl;

    @Mock
    private PersonRepository personRepository;

    @Test
    public void findPersonByFilterShouldReturnPersons() {

        String filter = "oscar";

        List<PersonEntity> persons = new ArrayList<>();
        persons.add(mock(PersonEntity.class));
        persons.add(mock(PersonEntity.class));
        persons.add(mock(PersonEntity.class));
        persons.add(mock(PersonEntity.class));

        when(personRepository.findAllPersonsFromFilters(filter, PageRequest.of(0, 15))).thenReturn(persons);

        List<PersonEntity> personList = personServiceImpl.findAllPersonsFromFilters(filter);
        assertNotNull(personList);
        assertEquals(4, personList.size());

    }
}
