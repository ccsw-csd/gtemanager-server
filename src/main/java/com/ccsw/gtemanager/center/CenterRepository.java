package com.ccsw.gtemanager.center;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.center.model.Center;

/**
 * CenterRepository: repositorio de datos de centros.
 *
 */
@Repository
public interface CenterRepository extends JpaRepository<Center, Long> {

	List<Center> findAll();
}
