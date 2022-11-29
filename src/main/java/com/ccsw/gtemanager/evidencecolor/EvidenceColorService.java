package com.ccsw.gtemanager.evidencecolor;

public interface EvidenceColorService {

    void modifyColor(Long personId, String rowColor);

    /**
     * Eliminar todos los registros de EvidenceColor.
     */
    void clear();
}
