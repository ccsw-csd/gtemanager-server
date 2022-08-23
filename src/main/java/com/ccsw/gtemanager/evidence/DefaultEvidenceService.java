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

import org.apache.poi.EncryptedDocumentException;
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

/**
 * DefaultEvidenceService: clase de implementación de EvidenceService. Contiene
 * métodos adicionales para el apoyo del procesamiento de datos de evidencias.
 * Se encarga de la gestión adicional de repositorios de comentarios, tipos, y
 * errores de evidencias, además de propiedades.
 */
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
	public List<EvidenceType> getEvidenceTypes() {
		return (List<EvidenceType>) evidenceTypeRepository.findAll();
	}

	@Override
	public List<Properties> getProperties() {
		return (List<Properties>) propertiesRepository.findAll();
	}

	@Override
	public EvidenceType findEvidenceType(String type) {
		List<EvidenceType> types = evidenceTypeRepository.findByCodeIgnoreCase(type);
		return types.isEmpty() ? null : types.get(0);
	}

	/**
	 * Obtener evidencia concreta dada una persona. Se devuelve nuevo Evidence si no
	 * se ha encontrado.
	 */
	@Override
	public Evidence findEvidencePerPerson(Person person) {
		List<Evidence> evidences = evidenceRepository.findByPersonId(person);
		return evidences.isEmpty() ? new Evidence(person) : evidences.get(0);
	}

	@Override
	public Person findPersonBySaga(String saga) {
		List<Person> people = personService.getBySaga(saga);
		return people.size() == 1 ? people.get(0) : null;
	}

	/**
	 * Obtener semana dado un periodo de tiempo.
	 * 
	 * @param period Periodo de tiempo en formato String, compatible
	 * @return String con día de inicio y fin de la semana
	 * @throws IllegalArgumentException No se ha introducido un periodo admisible
	 */
	protected String findWeekForPeriod(String period) throws IllegalArgumentException {
		String[] days = period.split(" - ");
		LocalDate d1 = LocalDate.parse(days[0], formatMonth);
		LocalDate d2 = LocalDate.parse(days[1], formatMonth);

		String week1 = findWeekForDay(d1);
		String week2 = findWeekForDay(d2);
		if (!week1.equals(week2))
			throw new IllegalArgumentException("El periodo introducido no es correcto. [period]");

		return week1;
	}

	/**
	 * Obtener semana dado un día concreto.
	 * 
	 * @param date Fecha compatible a averiguar
	 * @return String con día de inicio y fin de la semana
	 * @throws IllegalArgumentException No se ha introducido una fecha admisible
	 */
	protected String findWeekForDay(LocalDate date) throws IllegalArgumentException {
		try {
			String monday = date.with(DayOfWeek.MONDAY).format(formatMonth).toUpperCase();
			String sunday = date.with(DayOfWeek.SUNDAY).format(formatMonth).toUpperCase();

			return monday + " - " + sunday;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("La fecha introducida no es correcta. [date]");
		}
	}

	/**
	 * Leer y procesar un archivo de hoja de cálculo para obtener y almacenar
	 * evidencias.
	 * 
	 * Para ello, se obtiene la hoja de cálculo principal. Se obtienen las semanas
	 * correspondientes al periodo del informe, se procesan las propiedades, y se
	 * leen las líneas a partir de la 15 para los registros de evidencias. Se
	 * almacena un Evidence por cada Person. Si algún dato no es correcto, se
	 * registra en EvidenceError. Se detiene el proceso en caso de existir
	 * propiedades incorrectas.
	 */
	@Override
	public boolean uploadEvidence(FormDataDto upload) throws IllegalArgumentException, IOException {
		boolean ok = true;
		clearEvidenceData(upload.isDeleteComments());

		Workbook gteEvidences;
		try {
			gteEvidences = WorkbookFactory.create(upload.getFile().getInputStream());
		} catch (EncryptedDocumentException e) {
			throw new IllegalArgumentException("El archivo se encuentra encriptado.");
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Se ha producido un error leyendo el archivo. ¿Son las celdas correctas?");
		}

		Sheet sheet = gteEvidences.getSheetAt(0);

		List<String> weeks = obtainWeeks(LocalDate.parse(sheet.getRow(1).getCell(1).getStringCellValue(), formatMonth));
		parseProperties(sheet, weeks);

		List<Person> people = personService.getPeople();

		List<EvidenceType> types = getEvidenceTypes();

		Row currentRow = sheet.getRow(14);
		Person person = null;
		Evidence evidence = null;
		String sagaPrev = "";
		for (int i = 15; currentRow != null; i++) {
			ok = true;

			String fullName = currentRow.getCell(0).getStringCellValue();
			String saga = currentRow.getCell(1).getStringCellValue();
			String email = currentRow.getCell(2).getStringCellValue();
			String period = currentRow.getCell(9).getStringCellValue();
			String type = currentRow.getCell(10).getStringCellValue();

			String week;
			try {
				week = findWeekForPeriod(period);
			} catch (IllegalArgumentException e) {
				evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
				ok = false;
				sagaPrev = saga;
				currentRow = sheet.getRow(i);
				continue;
			}

			try {
				saga = parseSaga(saga);
			} catch (IndexOutOfBoundsException e) {
				evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
				ok = false;
				sagaPrev = saga;
				currentRow = sheet.getRow(i);
				continue;
			}

			if (!saga.equals(sagaPrev)) {
				person = new Person(saga);
			}
			if (!people.contains(person)) {
				person = findPersonBySaga(saga);
				if (person == null) {
					evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
					ok = false;
					sagaPrev = saga;
					currentRow = sheet.getRow(i);
					continue;
				}
			} else {
				person = people.get(people.indexOf(person));
				evidence = findEvidencePerPerson(person);
			}

			EvidenceType evidenceType = new EvidenceType(type);
			if (!types.contains(evidenceType)) {
				evidenceErrorRepository.save(new EvidenceError(fullName, saga, email, period, type));
				ok = false;
				sagaPrev = saga;
				currentRow = sheet.getRow(i);
				continue;
			} else
				evidenceType = types.get(types.indexOf(evidenceType));

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

	/**
	 * Leer y almacenar propiedades de la hoja de cálculo recibida. Deducir fechas
	 * de carga y periodo, nombre de usuario, semanas dentro del periodo, y número
	 * de semanas. Almacenar como objetos Properties.
	 * 
	 * @param sheet Hoja de cálculo a procesar
	 * @param weeks Listado de semanas dentro del periodo de evidencias
	 * @throws IllegalArgumentException Existen fechas no admisibles
	 */
	protected void parseProperties(Sheet sheet, List<String> weeks) throws IllegalArgumentException {
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
				pWeek.setValue(weeks.get(i - 1));
			} catch (IndexOutOfBoundsException e) {
				pWeek.setValue(null);
			}
			weekProperties.add(pWeek);
		}

		propertiesList.add(new Properties("LOAD_WEEKS", String.valueOf(weeks.size())));
		propertiesRepository.saveAll(propertiesList);
		propertiesRepository.saveAll(weekProperties);
	}

	/**
	 * Obtener listado de semanas dentro de un periodo de evidencias. Un periodo
	 * siempre estará comprendido dentro de un mes, por lo que no se darán más de 6
	 * semanas.
	 * 
	 * @param initialDate Fecha a deducir
	 * @return Listado de String con valores de las semanas
	 * @throws IllegalArgumentException No se ha introducido una fecha admisible
	 */
	protected List<String> obtainWeeks(LocalDate initialDate) throws IllegalArgumentException {
		LocalDate date = initialDate.withDayOfMonth(1);
		int currentMonth = initialDate.getMonthValue();
		List<String> weeks = new ArrayList<>();
		while (date.getMonthValue() == currentMonth) {
			weeks.add(findWeekForDay(date));
			date = date.plusDays(7).with(DayOfWeek.MONDAY);
		}

		return weeks;
	}

	/**
	 * Leer y deducir código saga de la persona implicada.
	 * 
	 * @param saga Código a procesar
	 * @return Código truncado y validado en formato numérico, o en longitud de 4
	 *         caracteres alfanuméricos
	 * @throws IllegalArgumentException No se ha introducido un código admisible
	 */
	protected String parseSaga(String saga) throws IllegalArgumentException {
		try {
			saga = saga.split("_")[1];
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Código Saga introducido no es válido. [saga]");
		}
		try {
			return String.valueOf(Long.parseLong(saga));
		} catch (NumberFormatException ex) {
			return saga.substring(saga.length() - 4);
		}
	}

	@Override
	public void clearEvidenceData(boolean deleteComments) {
		evidenceRepository.deleteAll();
		evidenceErrorRepository.deleteAll();
		propertiesRepository.deleteAll();
		if (deleteComments)
			evidenceCommentRepository.deleteAll();
	}

}
