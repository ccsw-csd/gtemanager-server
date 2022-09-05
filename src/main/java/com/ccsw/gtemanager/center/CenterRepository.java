package com.ccsw.gtemanager.center;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ccsw.gtemanager.center.model.Center;

public interface CenterRepository extends JpaRepository<Center, Long> {

	List<Center> findAll();
}
