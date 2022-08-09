package com.ccsw.gtemanager.evidence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.UploadDto;

@Service
@Transactional
public class DefaultEvidenceService implements EvidenceService {

	@Autowired
	private EvidenceRepository evidenceRepository;

	@Autowired
	private EvidenceErrorRepository evidenceErrorRepository;

	@Autowired
	private EvidenceCommentRepository evidenceCommentRepository;

	@Autowired
	private EvidenceTypeRepository evidenceTypeRepository;

	@Override
	public void uploadEvidence(UploadDto upload) throws IllegalArgumentException {

	}

	@Override
	public List<Evidence> getEvidences() {
		return (List<Evidence>) evidenceRepository.findAll();
	}

	@Override
	public List<EvidenceError> getEvidenceErrors() {
		return (List<EvidenceError>) evidenceErrorRepository.findAll();
	}

	@Override
	public List<EvidenceComment> getEvidenceComments() {
		return (List<EvidenceComment>) evidenceCommentRepository.findAll();
	}

	@Override
	public String findWeekForPeriod(String period) throws IllegalArgumentException {
		DateTimeFormatter format = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy")
				.toFormatter(Locale.getDefault());

		String[] days = period.split(" - ");
		LocalDate d1 = LocalDate.parse(days[0], format);
		LocalDate d2 = LocalDate.parse(days[1], format);

		LocalDate d1Monday = d1.with(DayOfWeek.MONDAY);
		LocalDate d1Sunday = d1.with(DayOfWeek.SUNDAY);
		LocalDate d2Monday = d2.with(DayOfWeek.MONDAY);
		LocalDate d2Sunday = d2.with(DayOfWeek.SUNDAY);

		if (d1.compareTo(d2) > 0 || !d1Monday.equals(d2Monday) || !d1Sunday.equals(d2Sunday))
			throw new IllegalArgumentException("El periodo introducido no es correcto. [period]");

		String monday = d1Monday.format(format).toUpperCase();
		String sunday = d1Sunday.format(format).toUpperCase();

		return monday + " - " + sunday;
	}

	@Override
	public List<EvidenceType> findEvidenceType(String type) {
		return evidenceTypeRepository.findByCode(type);
	}

	@Override
	public void emptyEvidences() {
		evidenceRepository.deleteAll();
	}

	@Override
	public void emptyErrors() {
		evidenceErrorRepository.deleteAll();
	}

	@Override
	public void emptyComments() {
		evidenceCommentRepository.deleteAll();
	}

}
