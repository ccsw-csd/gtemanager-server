package com.ccsw.gtemanager.recurrence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.recurrence.model.Recurrence;

@Repository
public interface RecurrenceRepository extends CrudRepository<Recurrence, Long>, JpaRepository<Recurrence, Long> {

    Long deleteByPersonId(Long personId);

}
