package com.ccsw.gtemanager.evidence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.Evidence;

@Repository
public interface PersonDatabaseRepository extends JpaRepository<Evidence, Long> {

    @Modifying
    @Query(value = "update person p set active = 0 where active = 1 and not exists (select 1 from personal.t_person t where t.username = p.username)", nativeQuery = true)
    void disablePerson();

    @Modifying
    @Query(value = "update person p set active = 1 where active = 0 and exists (select 1 from personal.t_person t where t.username = p.username)", nativeQuery = true)
    void enablePerson();

    @Modifying
    @Query(value = "insert into person(saga, username, email, name, lastname, center, businesscode, grade, active) " //
            + "select saga, username, email, name, lastname, coalesce((select center_id from center_transcode ct where ct.name = t.center), 99), businesscode, grade, 1 " //
            + "from personal.t_person t where not exists (select 1 from person p where p.username = t.username)", nativeQuery = true)
    void createNewPerson();

}
