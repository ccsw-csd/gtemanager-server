package com.ccsw.gtemanager.evidence;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ccsw.gtemanager.config.security.UserUtils;
import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.evidencecomment.EvidenceCommentService;
import com.ccsw.gtemanager.evidenceerror.EvidenceErrorService;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceErrorDto;
import com.ccsw.gtemanager.evidencetype.EvidenceTypeService;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.person.PersonService;
import com.ccsw.gtemanager.person.model.Person;
import com.ccsw.gtemanager.properties.PropertiesService;
import com.ccsw.gtemanager.properties.model.Properties;

/**
 * EvidenceServiceImpl: clase de implementación de EvidenceService. Contiene
 * métodos adicionales para el apoyo del procesamiento de datos de evidencias.
 * Se encarga de la gestión adicional de repositorios de comentarios, tipos, y
 * errores de evidencias, además de propiedades.
 */
@Service
@Transactional
public class EvidenceServiceImpl implements EvidenceService {

	private static final String PROPERTY_LOAD_DATE = "LOAD_DATE";
	private static final String PROPERTY_LOAD_USERNAME = "LOAD_USERNAME";
	private static final String PROPERTY_LOAD_WEEKS = "LOAD_WEEKS";
	private static final String PROPERTY_WEEK = "WEEK_";

	private static final int MONTH_START = 1;
	private static final int WEEK_OFFSET = 7;

	private static final int WEEK_LIST_1 = 0;
	private static final int WEEK_LIST_2 = 1;
	private static final int WEEK_LIST_3 = 2;
	private static final int WEEK_LIST_4 = 3;
	private static final int WEEK_LIST_5 = 4;
	private static final int WEEK_LIST_6 = 5;

	private static final int SHEET_0 = 0;

	private static final int ROW_2 = 1;
	private static final int ROW_3 = 2;
	private static final int ROW_10 = 9;
	private static final int ROW_15 = 14;

	private static final int COL_A = 0;
	private static final int COL_B = 1;
	private static final int COL_C = 2;
	private static final int COL_J = 9;
	private static final int COL_K = 10;

	private static final int EVIDENCE_LIST_START = 15;

	private static final String PERIOD_SEPARATOR = " - ";

	@Autowired
	private EvidenceErrorService evidenceErrorService;

	@Autowired
	private EvidenceCommentService evidenceCommentService;

	@Autowired
	private EvidenceTypeService evidenceTypeService;

	@Autowired
	private PersonService personService;

	@Autowired
	private PropertiesService propertiesService;

	@Autowired
	private EvidenceRepository evidenceRepository;

	private static DateTimeFormatter formatDate = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

	private static DateTimeFormatter formatDateTimeFile = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("LLLL dd, yyyy hh:mm a").toFormatter(Locale.getDefault());

	private static DateTimeFormatter formatDateTimeDB = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd/MM/yyyy HH:mm").toFormatter(Locale.getDefault());

	@Override
	public List<Evidence> getAll() {
		return (List<Evidence>) evidenceRepository.findAll();
	}

	/**
	 * Obtener semana dado un periodo de tiempo.
	 * 
	 * @param period Periodo de tiempo en formato String, compatible
	 * @return String con día de inicio y fin de la semana
	 * @throws IllegalArgumentException No se ha introducido un periodo admisible
	 */
	protected String findWeekForPeriod(String period) throws IllegalArgumentException {
		String[] days = period.split(PERIOD_SEPARATOR);
		LocalDate firstDay = null;
		LocalDate lastDay = null;
		try {
			firstDay = LocalDate.parse(days[0], formatDate);
			lastDay = LocalDate.parse(days[1], formatDate);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("El periodo introducido no es correcto.");
		}

		String firstWeek = findWeekForDay(firstDay);
		String secondWeek = findWeekForDay(lastDay);
		if (!firstWeek.equals(secondWeek))
			throw new IllegalArgumentException("El periodo introducido no es correcto.");

		return firstWeek;
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
			String monday = date.with(DayOfWeek.MONDAY).format(formatDate).toUpperCase();
			String sunday = date.with(DayOfWeek.SUNDAY).format(formatDate).toUpperCase();

			return monday + PERIOD_SEPARATOR + sunday;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("La fecha introducida no es correcta.");
		}
	}

	private List<Person> people;
	private List<String> weeks;
	private Map<Person, Evidence> evidences;
	private List<EvidenceType> types;

	/**
	 * Leer y procesar un archivo de hoja de cálculo para obtener y almacenar
	 * evidencias.
	 * 
	 * Para ello, se obtiene la hoja de cálculo principal. Se obtienen las semanas
	 * correspondientes al periodo del informe, se procesan las propiedades, y se
	 * leen las líneas a partir de la 15 para los registros de evidencias. Se
	 * almacena un Evidence por cada Person. Si algún dato no es correcto, se
	 * registra en EvidenceError. Se detiene el proceso en caso de existir
	 * propiedades incorrectas, o de no poder procesar fechas de periodo y
	 * ejecución.
	 */
	@Override
	public boolean uploadEvidence(FormDataDto upload) throws IllegalArgumentException {
		clearEvidenceData(upload.isDeleteComments());

		Sheet sheet = obtainSheet(upload.getFile());

		LocalDate fromDate = null;
		LocalDate toDate = null;
		LocalDateTime runDate = null;
		try {
			fromDate = LocalDate.parse(sheet.getRow(ROW_2).getCell(COL_B).getStringCellValue(), formatDate);
			toDate = LocalDate.parse(sheet.getRow(ROW_3).getCell(COL_B).getStringCellValue(), formatDate);
			runDate = LocalDateTime.parse(sheet.getRow(ROW_10).getCell(COL_B).getStringCellValue(), formatDateTimeFile);
			weeks = obtainWeeks(fromDate);
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"El informe no contiene fechas de periodo y/o ejecución válidas (B2, C2, B10).");
		}

		if (fromDate.compareTo(toDate) > 0)
			throw new IllegalArgumentException("El informe no se corresponde con un mes o periodo válido.");

		parseProperties(runDate, weeks);

		people = personService.getPeople();

		types = evidenceTypeService.getAll();

		evidences = new LinkedHashMap<>();

		List<EvidenceErrorDto> evidenceErrors = new ArrayList<>();

		Row currentRow = sheet.getRow(ROW_15);
		Person person = null;
		Evidence evidence = null;
		EvidenceType evidenceType = null;
		String sagaPrev = "";
		String week;
		for (int i = EVIDENCE_LIST_START; currentRow != null; i++) {
			String fullName = currentRow.getCell(COL_A).getStringCellValue();
			String saga = currentRow.getCell(COL_B).getStringCellValue();
			String email = currentRow.getCell(COL_C).getStringCellValue();
			String period = currentRow.getCell(COL_J).getStringCellValue();
			String type = currentRow.getCell(COL_K).getStringCellValue();

			boolean hasText = false;
			try {
				hasText = rowHasText(fullName, saga, email, period, type);
				week = findWeekForPeriod(period);
				saga = personService.parseSaga(saga);
				evidenceType = getEvidenceType(type);
				if (!saga.equals(sagaPrev))
					person = getPersonBySaga(saga);

				evidence = setTypeForWeek(getEvidenceForPerson(person), week, evidenceType);
			} catch (IllegalArgumentException | IndexOutOfBoundsException e) {
				if (hasText) {
					evidenceErrors.add(new EvidenceErrorDto(fullName, saga, email, period, type));
					sagaPrev = saga;
				}
				currentRow = sheet.getRow(i);
				continue;
			}

			evidences.put(person, evidence);

			sagaPrev = saga;
			currentRow = sheet.getRow(i);
		}

		saveAll(new ArrayList<>(evidences.values()));
		if (!evidenceErrors.isEmpty()) {
			evidenceErrorService.saveAll(evidenceErrors);
			return false;
		} else
			return true;
	}

	private boolean rowHasText(String fullName, String saga, String email, String period, String type) {
		return StringUtils.hasText(fullName) || StringUtils.hasText(saga) || StringUtils.hasText(email)
				|| StringUtils.hasText(period) || StringUtils.hasText(type);
	}

	private Person getPersonBySaga(String saga) throws IllegalArgumentException {
		try {
			return people.get(people.indexOf(new Person(saga)));
		} catch (IndexOutOfBoundsException e) {
			Person person = personService.getBySaga(saga);
			if (person == null)
				throw new IllegalArgumentException();
			else
				return person;
		}
	}

	private Evidence setTypeForWeek(Evidence evidence, String week, EvidenceType evidenceType)
			throws IllegalArgumentException {
		if (weeks.contains(week)) {
			if (week.equals(weeks.get(WEEK_LIST_1)))
				evidence.setEvidenceTypeW1(evidenceType);
			else if (week.equals(weeks.get(WEEK_LIST_2)))
				evidence.setEvidenceTypeW2(evidenceType);
			else if (week.equals(weeks.get(WEEK_LIST_3)))
				evidence.setEvidenceTypeW3(evidenceType);
			else if (week.equals(weeks.get(WEEK_LIST_4)))
				evidence.setEvidenceTypeW4(evidenceType);
			else if (week.equals(weeks.get(WEEK_LIST_5)))
				evidence.setEvidenceTypeW5(evidenceType);
			else if (week.equals(weeks.get(WEEK_LIST_6)))
				evidence.setEvidenceTypeW6(evidenceType);
		} else
			throw new IllegalArgumentException("No existen evidencias en el periodo.");

		return evidence;
	}

	private EvidenceType getEvidenceType(String type) throws IndexOutOfBoundsException {
		return types.get(types.indexOf(new EvidenceType(type)));
	}

	private Evidence getEvidenceForPerson(Person person) {
		Evidence evidence = evidences.get(person);
		return evidence != null ? evidence : new Evidence(person);
	}

	private Sheet obtainSheet(MultipartFile file) {
		try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
			return workbook.getSheetAt(SHEET_0);
		} catch (EncryptedDocumentException e) {
			throw new IllegalArgumentException("El archivo se encuentra encriptado.");
		} catch (Exception e) {
			throw new IllegalArgumentException(
					"Se ha producido un error leyendo el archivo. ¿Son las celdas correctas?");
		}
	}

	/**
	 * Leer y almacenar propiedades de la hoja de cálculo recibida. Deducir fecha de
	 * carga, nombre de usuario, semanas dentro del periodo, y número de semanas.
	 * Almacenar como objetos Properties.
	 * 
	 * @param runDate Fecha de ejecución de informe
	 * @param weeks   Listado de semanas dentro del periodo de evidencias
	 * @throws IllegalArgumentException Existen fechas no admisibles
	 */
	protected void parseProperties(LocalDateTime runDate, List<String> weeks) throws IllegalArgumentException {
		List<Properties> propertiesList = new ArrayList<>();
		propertiesList.add(new Properties(PROPERTY_LOAD_DATE, runDate.format(formatDateTimeDB)));

		propertiesList.add(new Properties(PROPERTY_LOAD_USERNAME, UserUtils.getUserDetails().getUsername()));

		List<Properties> weekProperties = new ArrayList<>();
		for (int i = 1; i <= 6; i++) {
			Properties pWeek = new Properties(PROPERTY_WEEK + i, null);
			try {
				pWeek.setValue(weeks.get(i - 1));
			} catch (IndexOutOfBoundsException e) {
				pWeek.setValue(null);
			}
			weekProperties.add(pWeek);
		}

		propertiesList.add(new Properties(PROPERTY_LOAD_WEEKS, String.valueOf(weeks.size())));
		propertiesService.saveAll(propertiesList);
		propertiesService.saveAll(weekProperties);
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
		LocalDate date = initialDate.withDayOfMonth(MONTH_START);
		int currentMonth = initialDate.getMonthValue();
		List<String> weekList = new ArrayList<>();
		while (date.getMonthValue() == currentMonth) {
			weekList.add(findWeekForDay(date));
			date = date.plusDays(WEEK_OFFSET).with(DayOfWeek.MONDAY);
		}

		return weekList;
	}

	/**
	 * Limpiar datos de evidencias, comentarios, errores, y parámetros.
	 * 
	 * @param deleteComments Controlar si se desea borrar comentarios
	 */
	public void clearEvidenceData(boolean deleteComments) {
		if (deleteComments)
			evidenceCommentService.clear();
		clear();
		evidenceErrorService.clear();
		propertiesService.clear();
	}

	@Override
	public void saveAll(List<Evidence> evidences) {
		evidenceRepository.saveAll(evidences);
	}

	@Override
	public void clear() {
		evidenceRepository.deleteAll();
	}
}
