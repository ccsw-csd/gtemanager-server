package com.ccsw.gtemanager.evidencemanager;

import com.ccsw.gtemanager.evidencemanager.model.EvidenceManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EvidenceManagerRepository extends JpaRepository<EvidenceManager, Long> {
	
}
