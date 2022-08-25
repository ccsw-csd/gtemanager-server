package com.ccsw.gtemanager.person;

import java.util.List;

import com.ccsw.gtemanager.person.model.PersonEntity;

public interface PersonService {

    List<PersonEntity> findAllPersonsFromFilters(String filter);

}
