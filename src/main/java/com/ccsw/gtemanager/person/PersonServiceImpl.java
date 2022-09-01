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

        PersonSpecification username = new PersonSpecification(
                new TernarySearchCriteria("username", null, null, ":", filter));

        PersonSpecification firstnameLastname = new PersonSpecification(
                new TernarySearchCriteria("name", "lastName", null, "concat :", filter));

        PersonSpecification lastnameFirstname = new PersonSpecification(
                new TernarySearchCriteria("lastName", "name", null, "concat :", filter));

        Specification<PersonEntity> specification = Specification.where(username)
                .or(lastnameFirstname.or(firstnameLastname));

        Page<PersonEntity> personsLike2 = personRepository.findAll(specification, PageRequest.of(0, 15));

        return personsLike2.getContent();
    }

}
