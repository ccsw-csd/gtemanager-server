package com.ccsw.gtemanager.evidence;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.properties.model.Properties;

public interface EvidenceService {

	List<Evidence> getEvidences();

	List<EvidenceError> getEvidenceErrors();

	List<EvidenceComment> getEvidenceComments();

	List<Properties> getProperties();

	EvidenceType findEvidenceType(String type);

	Evidence findEvidencePerPerson(Person person);

	boolean uploadEvidence(FormDataDto upload) throws IllegalArgumentException, IOException;

}
