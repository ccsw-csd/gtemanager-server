package com.ccsw.gtemanager.evidence;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.config.security.UserUtils;
import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.person.PersonService;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.properties.PropertiesRepository;
import com.ccsw.gtemanager.properties.model.Properties;

@Service
@Transactional
public class DefaultEvidenceService implements EvidenceService {

	@Autowired
	private PersonService personService;

	@Autowired
	private EvidenceRepository evidenceRepository;

	@Autowired
	private EvidenceErrorRepository evidenceErrorRepository;

	@Autowired
	private EvidenceCommentRepository evidenceCommentRepository;

	@Autowired
	private EvidenceTypeRepository evidenceTypeRepository;

	@Autowired
	private PropertiesRepository propertiesRepository;

	private static DateTimeFormatter formatMonth = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

	private static DateTimeFormatter formatDateTime = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("LLLL dd, yyyy hh:mm a").toFormatter(Locale.getDefault());

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
	public List<Properties> getProperties() {
		return (List<Properties>) propertiesRepository.findAll();
	}

	@Override
	public boolean uploadEvidence(FormDataDto upload) throws IllegalArgumentException, IOException {
		boolean ok = true;
		clearEvidenceData(upload.isDeleteComments());

		Workbook gteEvidences = WorkbookFactory.create(upload.getFile().getInputStream());

		Sheet sheet = gteEvidences.getSheetAt(0);

		List<String> weeks = obtainWeeks(LocalDate.parse(sheet.getRow(1).getCell(1).getStringCellValue(), formatMonth));
		parseProperties(sheet, weeks);

		Row currentRow = sheet.getRow(14);
		String sagaPrev = "";
		for (int i = 15; currentRow != null; i++) {

			String fullName = currentRow.getCell(0).getStringCellValue();
			String saga = currentRow.getCell(1).getStringCellValue();
			String email = currentRow.getCell(2).getStringCellValue();
			String period = currentRow.getCell(9).getStringCellValue();
			String type = currentRow.getCell(10).getStringCellValue();

			String week;
			try {
				week = findWeekForPeriod(period);
			} catch (IllegalArgumentException ex) {
				evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
				ok = false;
				sagaPrev = saga;
				currentRow = sheet.getRow(i);
				continue;
			}

			saga = parseSaga(saga);

			Person person = null;
			if (!saga.equals(sagaPrev)) {
				person = findPersonBySaga(saga);
				if (person == null) {
					evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
					ok = false;
					sagaPrev = saga;
					currentRow = sheet.getRow(i);
					continue;
				}
			}

			Evidence evidence = findEvidencePerPerson(person);

			EvidenceType evidenceType = findEvidenceType(type);
			if (evidenceType == null) {
				evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
				ok = false;
				sagaPrev = saga;
				currentRow = sheet.getRow(i);
				continue;
			}

			if (weeks.contains(week)) {
				if (week.equals(weeks.get(0)))
					evidence.setEvidenceTypeW1(evidenceType);
				else if (week.equals(weeks.get(1)))
					evidence.setEvidenceTypeW2(evidenceType);
				else if (week.equals(weeks.get(2)))
					evidence.setEvidenceTypeW3(evidenceType);
				else if (week.equals(weeks.get(3)))
					evidence.setEvidenceTypeW4(evidenceType);
				else if (week.equals(weeks.get(4)))
					evidence.setEvidenceTypeW5(evidenceType);
				else if (week.equals(weeks.get(5)))
					evidence.setEvidenceTypeW6(evidenceType);
			} else {
				evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
				ok = false;
				sagaPrev = saga;
				currentRow = sheet.getRow(i);
				continue;
			}

			if (ok)
				evidenceRepository.save(evidence);

			sagaPrev = saga;
			currentRow = sheet.getRow(i);
		}
		gteEvidences.close();
		return ok;
	}

	protected void clearEvidenceData(boolean deleteComments) {
		emptyEvidences();
		emptyProperties();
		emptyErrors();
		if (deleteComments)
			emptyComments();
	}

	private String parseSaga(String saga) {
		saga = saga.split("_")[1];
		try {
			return String.valueOf(Long.parseLong(saga));
		} catch (NumberFormatException ex) {
			return saga.substring(saga.length() - 4);
		}
	}

	private List<String> obtainWeeks(LocalDate initialDate) throws IllegalArgumentException {
		LocalDate date = initialDate;
		List<String> weeks = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			if (date.getMonthValue() == initialDate.getMonthValue()) {
				weeks.add(findWeekForDay(date));
				date = date.plusDays(7);
			}
		}

		return weeks;
	}

	private void parseProperties(Sheet sheet, List<String> weeks) {
		String sFromDate = sheet.getRow(1).getCell(1).getStringCellValue();
		String sToDate = sheet.getRow(2).getCell(1).getStringCellValue();
		String sRunDate = sheet.getRow(9).getCell(1).getStringCellValue();

		LocalDate fromDate = null;
		LocalDate toDate = null;
		LocalDateTime runDate = null;
		try {
			fromDate = LocalDate.parse(sFromDate, formatMonth);
			toDate = LocalDate.parse(sToDate, formatMonth);
			runDate = LocalDateTime.parse(sRunDate, formatDateTime);

			if (fromDate.compareTo(toDate) > 0)
				throw new IllegalArgumentException("El informe no se corresponde con un mes o periodo válido. [date]");
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException(
					"El informe no contiene fechas de periodo y/o ejecución válidas (B2, C2, B10). [date]");
		}

		List<Properties> propertiesList = new ArrayList<>();
		propertiesList.add(new Properties("LOAD_DATE", runDate.format(formatDateTime)));

		propertiesList.add(new Properties("LOAD_USERNAME", UserUtils.getUserDetails().getUsername()));

		List<Properties> weekProperties = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			Properties pWeek = new Properties("WEEK_" + i, null);
			try {
				pWeek.setValue(weeks.get(i));
			} catch (IndexOutOfBoundsException e) {
				pWeek.setValue(null);
			}
			weekProperties.add(pWeek);
		}

		propertiesList.add(new Properties("LOAD_WEEKS", String.valueOf(weeks.size())));
		propertiesRepository.saveAll(propertiesList);
		propertiesRepository.saveAll(weekProperties);
	}

	@Override
	public String findWeekForPeriod(String period) throws IllegalArgumentException {
		String[] days = period.split(" - ");
		LocalDate d1 = LocalDate.parse(days[0], formatMonth);
		LocalDate d2 = LocalDate.parse(days[1], formatMonth);

		LocalDate d1Monday = d1.with(DayOfWeek.MONDAY);
		LocalDate d1Sunday = d1.with(DayOfWeek.SUNDAY);
		LocalDate d2Monday = d2.with(DayOfWeek.MONDAY);
		LocalDate d2Sunday = d2.with(DayOfWeek.SUNDAY);

		if (d1.compareTo(d2) > 0 || !d1Monday.equals(d2Monday) || !d1Sunday.equals(d2Sunday))
			throw new IllegalArgumentException("El periodo introducido no es correcto. [period]");

		String monday = d1Monday.format(formatMonth).toUpperCase();
		String sunday = d1Sunday.format(formatMonth).toUpperCase();

		return monday + " - " + sunday;
	}

	@Override
	public String findWeekForDay(LocalDate date) throws IllegalArgumentException {
		String monday = date.with(DayOfWeek.MONDAY).format(formatMonth).toUpperCase();
		String sunday = date.with(DayOfWeek.SUNDAY).format(formatMonth).toUpperCase();

		return monday + " - " + sunday;
	}

	@Override
	public Person findPersonBySaga(String saga) {
		List<Person> people = personService.getBySaga(saga);
		return people.size() == 1 ? people.get(0) : null;
	}

	@Override
	public Evidence findEvidencePerPerson(Person person) {
		List<Evidence> evidences = evidenceRepository.findByPersonId(person);
		return evidences.isEmpty() ? new Evidence(person) : evidences.get(0);
	}

	@Override
	public EvidenceType findEvidenceType(String type) {
		List<EvidenceType> types = evidenceTypeRepository.findByCodeIgnoreCase(type);
		return types.isEmpty() ? null : types.get(0);
	}

	private void emptyEvidences() {
		evidenceRepository.deleteAll();
	}

	private void emptyErrors() {
		evidenceErrorRepository.deleteAll();
	}

	private void emptyComments() {
		evidenceCommentRepository.deleteAll();
	}

	private void emptyProperties() {
		propertiesRepository.deleteAll();
	}

}
