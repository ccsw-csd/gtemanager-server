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

	boolean uploadEvidence(FormDataDto upload) throws IllegalArgumentException, IOException;

	List<Evidence> getEvidences();

	List<EvidenceError> getEvidenceErrors();

	List<EvidenceComment> getEvidenceComments();

	List<Properties> getProperties();

	EvidenceType findEvidenceType(String type);

	String findWeekForPeriod(String period) throws IllegalArgumentException;

	Evidence findEvidencePerPerson(Person person);

	String findWeekForDay(LocalDate date) throws IllegalArgumentException;

	Person findPersonBySaga(String saga);

}
