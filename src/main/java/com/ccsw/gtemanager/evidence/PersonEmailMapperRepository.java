package com.ccsw.gtemanager.evidence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidence.model.PersonEmailMapper;

@Repository
public interface PersonEmailMapperRepository extends JpaRepository<PersonEmailMapper, Long> {

}
