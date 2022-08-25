package com.ccsw.gtemanager.person;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.gtemanager.person.model.PersonEntity;

public interface PersonRepository extends CrudRepository<PersonEntity, Long>, JpaSpecificationExecutor<PersonEntity> {

    @Query("select p from PersonEntity p where concat(name, ' ', lastname, ' ', username) LIKE %:filter% order by name, lastname asc")
    List<PersonEntity> findAllPersonsFromFilters(String filter, Pageable pageable);

}
