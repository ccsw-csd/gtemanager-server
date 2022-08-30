package com.ccsw.gtemanager.evidenceview;

import java.util.List;

import com.ccsw.gtemanager.evidencecomment.model.EvidenceComment;
import com.ccsw.gtemanager.evidenceview.model.EvidenceView;

public interface EvidenceViewService {
	
	List<EvidenceView> findOrderedByGeography(Long idGeography);
}
