package com.ccsw.gtemanager.evidence;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.comment.CommentService;
import com.ccsw.gtemanager.common.criteria.SearchCriteria;
import com.ccsw.gtemanager.common.exception.BadRequestException;
import com.ccsw.gtemanager.common.exception.UnprocessableEntityException;
import com.ccsw.gtemanager.common.exception.UnsupportedMediaTypeException;
import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.evidence.model.PersonEmailMapper;
import com.ccsw.gtemanager.evidencecolor.EvidenceColorService;
import com.ccsw.gtemanager.evidenceerror.EvidenceErrorService;
import com.ccsw.gtemanager.evidenceerror.model.EvidenceError;
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

    private static final String ORIGINAL_FROM_DATE = "ORIGINAL_FROM_DATE";
    private static final String XLS_FILE_FORMAT = "application/vnd.ms-excel";
    private static final String XLSX_FILE_FORMAT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    public static final List<String> ALLOWED_FORMATS = Arrays.asList(XLS_FILE_FORMAT, XLSX_FILE_FORMAT);

    private static final int MONTH_START = 1;
    private static final int WEEK_OFFSET = 7;

    private static final int FIRST_SHEET = 0;

    private static final int ROW_PROPERTY_FROM_DATE = 1;
    private static final int ROW_PROPERTY_TO_DATE = 2;

    private static final int ROW_EVIDENCE_LIST_START = 14;
    private static final int ROW_EVIDENCE_LIST_NEXT = ROW_EVIDENCE_LIST_START + 1;

    private static final int COL_PROPERTY_VALUE = 1;

    private static final int COL_EVIDENCE_NAME = 0;
    private static final int COL_EVIDENCE_SAGA = 1;
    private static final int COL_EVIDENCE_EMAIL = 2;
    private static final int COL_EVIDENCE_PERIOD = 9;
    private static final int COL_EVIDENCE_STATUS = 10;

    private static final String PERIOD_SEPARATOR = " - ";

    private static final int MAX_ERROR_MESSAGE_SIZE = 3499;

    @Autowired
    private EvidenceErrorService evidenceErrorService;

    @Autowired
    private CommentService evidenceCommentService;

    @Autowired
    private EvidenceColorService evidenceColorService;

    @Autowired
    private EvidenceTypeService evidenceTypeService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private PersonDatabaseRepository personDatabaseRepository;

    @Autowired
    private EvidenceRepository evidenceRepository;

    @Autowired
    private PersonEmailMapperRepository personEmailMapperRepository;

    private static DateTimeFormatter formatDate = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

    @Override
    public List<Evidence> findByGeography(Long idGeography) {
        EvidenceSpecification geography = new EvidenceSpecification(new SearchCriteria("center", ":", idGeography));
        Specification<Evidence> specification = Specification.where(geography);
        return this.evidenceRepository.findAll(specification, Sort.by(Sort.Direction.ASC, "person.center.name"));
    }

    @Override
    public List<Evidence> getEvidences() {

        return evidenceRepository.findAll();
    }

    @Override
    public Map<String, EvidenceType> getTypesForEvidence(Evidence evidence, List<String> weeks) {
        if (evidence == null)
            throw new IllegalArgumentException();

        Map<String, EvidenceType> typesMap = new LinkedHashMap<>();

        if (evidence.getEvidenceTypeW1() != null)
            typesMap.put(weeks.get(0), evidence.getEvidenceTypeW1());
        if (evidence.getEvidenceTypeW2() != null)
            typesMap.put(weeks.get(1), evidence.getEvidenceTypeW2());
        if (evidence.getEvidenceTypeW3() != null)
            typesMap.put(weeks.get(2), evidence.getEvidenceTypeW3());
        if (evidence.getEvidenceTypeW4() != null)
            typesMap.put(weeks.get(3), evidence.getEvidenceTypeW4());
        if (weeks.size() >= 5 && evidence.getEvidenceTypeW5() != null)
            typesMap.put(weeks.get(4), evidence.getEvidenceTypeW5());
        if (weeks.size() == 6 && evidence.getEvidenceTypeW6() != null)
            typesMap.put(weeks.get(5), evidence.getEvidenceTypeW6());

        return typesMap;
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
     * propiedades incorrectas, o de no poder procesar fechas de periodo y
     * ejecución.
     */
    @Override
    public boolean uploadEvidence(FormDataDto upload) throws ResponseStatusException {

        if (upload.getFile() == null)
            throw new UnsupportedMediaTypeException();

        if (!ALLOWED_FORMATS.contains(upload.getFile().getContentType()))
            throw new UnprocessableEntityException();

        updatePersonDatabase();
        clearReport(upload.isDeleteComments(), upload.isDeleteColors());

        Sheet sheet = obtainSheet(upload.getFile());

        LocalDate fromDate = null;
        LocalDate toDate = null;
        LocalDateTime runDate = null;
        List<Properties> properties;
        List<String> weeks;
        try {
            String originalFromDate = sheet.getRow(ROW_PROPERTY_FROM_DATE).getCell(COL_PROPERTY_VALUE).getStringCellValue();
            fromDate = LocalDate.parse(originalFromDate, formatDate);
            toDate = LocalDate.parse(sheet.getRow(ROW_PROPERTY_TO_DATE).getCell(COL_PROPERTY_VALUE).getStringCellValue(), formatDate);
            if (fromDate.isAfter(toDate))
                throw new BadRequestException("El informe no contiene fechas de periodo válidas (B2, C2).");

            runDate = LocalDateTime.now();
            weeks = obtainWeeks(fromDate);
            properties = propertiesService.parseProperties(runDate, weeks);
            properties.add(new Properties(ORIGINAL_FROM_DATE, originalFromDate));

        } catch (NullPointerException | DateTimeException e) {
            throw new BadRequestException("El informe no contiene fecha de ejecución válida (B10).");
        }

        Map<String, Person> emailPersonMap = createEmailPersonMap();

        List<EvidenceType> evidenceTypes = evidenceTypeService.getEvidenceTypes();

        Map<Person, Evidence> evidences = new LinkedHashMap<>();
        List<EvidenceErrorDto> evidenceErrors = new ArrayList<>();

        Row currentRow = sheet.getRow(ROW_EVIDENCE_LIST_START);
        Person person = null;
        Evidence evidence = null;
        for (int i = ROW_EVIDENCE_LIST_NEXT; currentRow != null; i++) {
            String fullName = currentRow.getCell(COL_EVIDENCE_NAME).getStringCellValue();
            String saga = currentRow.getCell(COL_EVIDENCE_SAGA).getStringCellValue();
            String email = currentRow.getCell(COL_EVIDENCE_EMAIL).getStringCellValue();
            String period = currentRow.getCell(COL_EVIDENCE_PERIOD).getStringCellValue();
            String type = currentRow.getCell(COL_EVIDENCE_STATUS).getStringCellValue();
            String message;

            if (StringUtils.hasText(fullName) || StringUtils.hasText(saga) || StringUtils.hasText(email) || StringUtils.hasText(period) || StringUtils.hasText(type)) {
                try {
                    saga = personService.parseSaga(saga);
                    email = email.toLowerCase();

                    person = emailPersonMap.get(email);
                    if (person == null) {
                        throw new Exception("No existe persona con el email especificado.");
                    }

                    evidence = setTypeForWeek(getEvidenceForPerson(evidences, person, saga), weeks.indexOf(getWeekForPeriod(period)), getEvidenceType(evidenceTypes, type));
                    evidences.put(person, evidence);
                } catch (Exception e) {
                    message = e.getLocalizedMessage();
                    if (message.length() >= MAX_ERROR_MESSAGE_SIZE) {
                        message = message.substring(0, MAX_ERROR_MESSAGE_SIZE);
                    }
                    evidenceErrors.add(new EvidenceErrorDto(fullName, saga, email, period, type, message));
                }
            }
            currentRow = sheet.getRow(i);
        }

        saveReport(properties, evidences, evidenceErrors);
        return evidenceErrors.isEmpty();
    }

    /**
     * Obtener evidencia para una persona determinada. Se busca en el mapa de
     * evidencias en procesamiento, y se devuelve un Evidence vacío, con la persona
     * asociada, en caso de no encontrarse.
     *
     * @param evidences Map de Person y Evidence en el que buscar
     * @param person    Person por el que buscar
     * @param saga      Saga importado que almacenar en el caso de que sea nuevo
     * @return Evidence hallado o Evidence nuevo en caso de no hallarse
     */
    private Evidence getEvidenceForPerson(Map<Person, Evidence> evidences, Person person, String saga) {
        Evidence evidence = evidences.get(person);
        return evidence != null ? evidence : new Evidence(person, saga);
    }

    private void updatePersonDatabase() {

        personDatabaseRepository.disablePerson();

        personDatabaseRepository.enablePerson();

        personDatabaseRepository.createNewPerson();

    }

    @Override
    public void saveAll(List<Evidence> evidences) {
        evidenceRepository.saveAll(evidences);
    }

    @Override
    public void clear() {
        evidenceRepository.deleteAllInBatch();
    }

    /**
     * Obtener la hoja de cálculo principal dado un fichero
     * 
     * @param file Archivo de hojas de cálculo
     * @return Hoja de cálculo elegida
     * @throws BadRequestException No es posible leer el fichero proporcionado
     */
    private Sheet obtainSheet(MultipartFile file) throws BadRequestException {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            return workbook.getSheetAt(FIRST_SHEET);
        } catch (Exception e) {
            throw new BadRequestException("Se ha producido un error leyendo el archivo. Compruebe la validez de los datos y que no se encuentra encriptado.");
        }
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

    @Override
    public Map<String, Person> createEmailPersonMap() {

        Map<String, Person> map = new HashMap<>();

        List<PersonEmailMapper> peopleMapper = personEmailMapperRepository.findAll();
        peopleMapper.forEach(item -> map.put(item.getEmail().toLowerCase(), item.getPerson()));

        List<Person> people = personService.getPeople();
        people.forEach(item -> map.put(item.getEmail(), item));

        return map;
    }

    /**
     * Obtener semana dado un periodo de tiempo.
     * 
     * @param period Periodo de tiempo en formato String, compatible
     * @return String con día de inicio y fin de la semana
     * @throws IllegalArgumentException No se ha introducido un periodo admisible
     */
    protected String getWeekForPeriod(String period) throws IllegalArgumentException {
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

            return String.format("%s%s%s", monday, PERIOD_SEPARATOR, sunday);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("La fecha introducida no es correcta.");
        }
    }

    /**
     * Obtener tipo de evidencia dado un código determinado. No se devuelve un valor
     * NULL, en su lugar lanzando una excepción, al no poder procesarse una
     * evidencia sin EvidenceType correctos.
     * 
     * @param types List de EvidenceType en el que buscar
     * @param type  Tipo de evidencia por el que buscar
     * @return EvidenceType hallado en base de datos
     * @throws IndexOutOfBoundsException No se ha podido encontrar el tipo
     *                                   especificado
     */
    private EvidenceType getEvidenceType(List<EvidenceType> types, String type) throws IndexOutOfBoundsException {
        return types.get(types.indexOf(new EvidenceType(type)));
    }

    /**
     * Almacenar tipo de evidencia procesado en la semana correspondiente.
     * 
     * @param evidence     Evidencia sobre la que almacenar el dato
     * @param week         Indicador de semana en proceso según listado de semanas
     * @param evidenceType Tipo de evidencia determinado
     * @return Evidence con tipo de evidencia registrado en la semana
     * @throws IllegalArgumentException No se ha especificado una semana correcta
     */
    private Evidence setTypeForWeek(Evidence evidence, int week, EvidenceType evidenceType) throws IllegalArgumentException {
        switch (week) {
        case 0:
            evidence.setEvidenceTypeW1(evidenceType);
            break;
        case 1:
            evidence.setEvidenceTypeW2(evidenceType);
            break;
        case 2:
            evidence.setEvidenceTypeW3(evidenceType);
            break;
        case 3:
            evidence.setEvidenceTypeW4(evidenceType);
            break;
        case 4:
            evidence.setEvidenceTypeW5(evidenceType);
            break;
        case 5:
            evidence.setEvidenceTypeW6(evidenceType);
            break;
        default:
            throw new IllegalArgumentException("No existen evidencias en el periodo.");
        }

        return evidence;
    }

    /**
     * Limpiar datos de parámetros, evidencias, comentarios, y errores.
     * 
     * @param deleteComments Controlar si se desea borrar comentarios
     */
    public void clearReport(boolean deleteComments, boolean deleteColors) {
        //propertiesService.clear();
        if (deleteComments) {
            evidenceCommentService.clear();
            //personEmailMapperRepository.deleteAllInBatch();
        }
        if (deleteColors) {
            evidenceColorService.clear();
        }
        clear();
        evidenceErrorService.clear();
    }

    /**
     * Almacenar datos de parámetros, evidencias, y errores.
     * 
     * @param properties     Listado de propiedades del informe
     * @param evidences      Map de Person y Evidence con evidencias del informe
     * @param evidenceErrors Listado de evidencias erróneas
     */
    private void saveReport(List<Properties> properties, Map<Person, Evidence> evidences, List<EvidenceErrorDto> evidenceErrors) {
        propertiesService.saveAll(properties);
        saveAll(new ArrayList<>(evidences.values()));
        evidenceErrorService.saveAll(evidenceErrors);
    }

    @Override
    public void setEmailNotificationSentForPersonId(Long id) {
        Evidence evidence = evidenceRepository.findByPersonId(id);
        evidence.setEmailNotificationSent(true);
        evidenceRepository.save(evidence);
    }

    @Override
    public void mapPerson(Long personId, String email) {

        Properties propertyOriginalDate = propertiesService.getProperty(ORIGINAL_FROM_DATE);

        String originalFromDate = propertyOriginalDate.getValue();
        LocalDate fromDate = LocalDate.parse(originalFromDate, formatDate);

        List<String> weeks = obtainWeeks(fromDate);

        Person person = personService.findById(personId);
        List<EvidenceError> evidenceErrors = evidenceErrorService.findByEmail(email);
        List<EvidenceType> evidenceTypes = evidenceTypeService.getEvidenceTypes();
        Evidence evidence = new Evidence(person);

        for (EvidenceError error : evidenceErrors) {

            String period = error.getPeriod();
            String type = error.getStatus();

            evidence.setSaga(error.getSaga());
            setTypeForWeek(evidence, weeks.indexOf(getWeekForPeriod(period)), getEvidenceType(evidenceTypes, type));
        }

        PersonEmailMapper personEmailMapper = new PersonEmailMapper();
        personEmailMapper.setPerson(person);
        personEmailMapper.setEmail(email);
        personEmailMapperRepository.save(personEmailMapper);

        evidenceErrorService.deleteAll(evidenceErrors);
        evidenceRepository.save(evidence);
    }
}
