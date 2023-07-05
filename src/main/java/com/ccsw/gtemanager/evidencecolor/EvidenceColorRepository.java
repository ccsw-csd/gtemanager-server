package com.ccsw.gtemanager.evidencecolor;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ccsw.gtemanager.evidencecolor.model.EvidenceColor;

@Repository
public interface EvidenceColorRepository extends JpaRepository<EvidenceColor, Long> {

    Optional<EvidenceColor> getByPersonId(Long personId);

    @Modifying
    @Query("delete from EvidenceColor c where c.rowColor <> 'row-color-1'")
    Integer removeAllWithoutGreyColor();

}
