package com.ccsw.gtemanager.center;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.center.model.Center;

/**
 * TODO DOCS
 *
 */
@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

}
