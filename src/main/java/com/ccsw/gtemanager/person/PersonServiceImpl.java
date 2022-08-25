package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.gtemanager.config.mapper.BeanMapper;
import com.ccsw.gtemanager.person.model.PersonEntity;

@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    @Autowired
    PersonRepository personRepository;

    @Autowired
    BeanMapper beanMapper;

    @Override
    public List<PersonEntity> findAllPersonsFromFilters(String filter) {
        List<PersonEntity> personsLike = this.personRepository.findAllPersonsFromFilters(filter, PageRequest.of(0, 15));
        return personsLike;
    }

}
