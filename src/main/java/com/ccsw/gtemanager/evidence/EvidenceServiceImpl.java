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
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.common.exception.BadRequestException;
import com.ccsw.gtemanager.common.exception.UnprocessableEntityException;
import com.ccsw.gtemanager.common.exception.UnsupportedMediaTypeException;
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

    private static final String XLS_FILE_FORMAT = "application/vnd.ms-excel";
    private static final String XLSX_FILE_FORMAT = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
    private static final List<String> ALLOWED_FORMATS = Arrays.asList(XLS_FILE_FORMAT, XLSX_FILE_FORMAT);

    private static final String PROPERTY_LOAD_DATE = "LOAD_DATE";
    private static final String PROPERTY_LOAD_USERNAME = "LOAD_USERNAME";
    private static final String PROPERTY_LOAD_WEEKS = "LOAD_WEEKS";
    private static final String PROPERTY_WEEK = "WEEK_";

    private static final int MONTH_START = 1;
    private static final int WEEK_OFFSET = 7;
    private static final int MIN_WEEKS = 1;
    private static final int MAX_WEEKS = 6;

    private static final int FIRST_SHEET = 0;

    private static final int ROW_PROPERTY_FROM_DATE = 1;
    private static final int ROW_PROPERTY_TO_DATE = 2;
    private static final int ROW_PROPERTY_RUNDATE = 9;

    private static final int ROW_EVIDENCE_LIST_START = 14;
    private static final int ROW_EVIDENCE_LIST_NEXT = ROW_EVIDENCE_LIST_START + 1;

    private static final int COL_PROPERTY_VALUE = 1;

    private static final int COL_EVIDENCE_NAME = 0;
    private static final int COL_EVIDENCE_SAGA = 1;
    private static final int COL_EVIDENCE_EMAIL = 2;
    private static final int COL_EVIDENCE_PERIOD = 9;
    private static final int COL_EVIDENCE_STATUS = 10;

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

    private List<Properties> propertiesList;
    private List<Properties> weekProperties;
    private List<Person> people;
    private List<EvidenceType> types;
    private List<String> weeks;
    private Map<Person, Evidence> evidences;
    private List<EvidenceErrorDto> evidenceErrors;

    private static DateTimeFormatter formatDate = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());
    private static DateTimeFormatter formatDateTimeFile = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("LLLL dd, yyyy hh:mm a").toFormatter(Locale.getDefault());
    private static DateTimeFormatter formatDateTimeDB = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd/MM/yyyy HH:mm").toFormatter(Locale.getDefault());

    @Override
    public List<Evidence> getEvidences() {
        return evidenceRepository.findAll();
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
        else if (!ALLOWED_FORMATS.contains(upload.getFile().getContentType()))
            throw new UnprocessableEntityException();

        Sheet sheet = obtainSheet(upload.getFile());

        LocalDate fromDate = null;
        LocalDate toDate = null;
        LocalDateTime runDate = null;
        try {
            fromDate = LocalDate.parse(
                    sheet.getRow(ROW_PROPERTY_FROM_DATE).getCell(COL_PROPERTY_VALUE).getStringCellValue(), formatDate);
            toDate = LocalDate.parse(
                    sheet.getRow(ROW_PROPERTY_TO_DATE).getCell(COL_PROPERTY_VALUE).getStringCellValue(), formatDate);
            if (fromDate.compareTo(toDate) > 0)
                throw new BadRequestException("El informe no contiene fechas de periodo válidas (B2, C2).");

            runDate = LocalDateTime.parse(
                    sheet.getRow(ROW_PROPERTY_RUNDATE).getCell(COL_PROPERTY_VALUE).getStringCellValue(),
                    formatDateTimeFile);
            weeks = obtainWeeks(fromDate);
            parseProperties(runDate);
        } catch (NullPointerException | DateTimeException e) {
            throw new BadRequestException("El informe no contiene fecha de ejecución válida (B10).");
        }

        people = personService.getPeople();

        types = evidenceTypeService.getEvidenceTypes();

        evidences = new LinkedHashMap<>();

        evidenceErrors = new ArrayList<>();

        Row currentRow = sheet.getRow(ROW_EVIDENCE_LIST_START);
        Person person = null;
        Evidence evidence = null;
        String previousSaga = "";
        for (int i = ROW_EVIDENCE_LIST_NEXT; currentRow != null; i++) {
            String fullName = currentRow.getCell(COL_EVIDENCE_NAME).getStringCellValue();
            String saga = currentRow.getCell(COL_EVIDENCE_SAGA).getStringCellValue();
            String email = currentRow.getCell(COL_EVIDENCE_EMAIL).getStringCellValue();
            String period = currentRow.getCell(COL_EVIDENCE_PERIOD).getStringCellValue();
            String type = currentRow.getCell(COL_EVIDENCE_STATUS).getStringCellValue();

            if (StringUtils.hasText(fullName) || StringUtils.hasText(saga) || StringUtils.hasText(email)
                    || StringUtils.hasText(period) || StringUtils.hasText(type)) {
                try {
                    saga = personService.parseSaga(saga);
                    if (!saga.equals(previousSaga) || person == null)
                        person = getPersonBySaga(saga);

                    evidence = setTypeForWeek(getEvidenceForPerson(person), weeks.indexOf(getWeekForPeriod(period)),
                            getEvidenceType(type));
                    evidences.put(person, evidence);
                } catch (IllegalArgumentException | IndexOutOfBoundsException e) {
                    evidenceErrors.add(new EvidenceErrorDto(fullName, saga, email, period, type));
                }
                previousSaga = saga;
            }
            currentRow = sheet.getRow(i);
        }

        clearReport(upload.isDeleteComments());
        saveReport();
        return evidenceErrors.isEmpty();
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
     * @throws UnreadableReportException No es posible leer el fichero proporcionado
     */
    private Sheet obtainSheet(MultipartFile file) throws BadRequestException {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            return workbook.getSheetAt(FIRST_SHEET);
        } catch (Exception e) {
            throw new BadRequestException(
                    "Se ha producido un error leyendo el archivo. Compruebe la validez de los datos y que no se encuentra encriptado.");
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

    /**
     * Leer y almacenar propiedades de la hoja de cálculo recibida. Deducir fecha de
     * carga, nombre de usuario, semanas dentro del periodo, y número de semanas.
     * Almacenar como objetos Properties.
     * 
     * @param runDate Fecha de ejecución de informe
     * @throws DateTimeException Existen fechas no admisibles
     */
    protected void parseProperties(LocalDateTime runDate) throws DateTimeException {
        propertiesList = new ArrayList<>();
        propertiesList.add(new Properties(PROPERTY_LOAD_DATE, runDate.format(formatDateTimeDB)));

        propertiesList.add(new Properties(PROPERTY_LOAD_USERNAME, UserUtils.getUserDetails().getUsername()));

        weekProperties = new ArrayList<>();
        for (int i = MIN_WEEKS; i <= MAX_WEEKS; i++) {
            Properties weekProperty = new Properties(PROPERTY_WEEK + i, null);
            try {
                weekProperty.setValue(weeks.get(i - 1));
            } catch (IndexOutOfBoundsException e) {
                weekProperty.setValue(null);
            }
            weekProperties.add(weekProperty);
        }

        propertiesList.add(new Properties(PROPERTY_LOAD_WEEKS, String.valueOf(weeks.size())));
    }

    /**
     * Obtener persona dado un código saga determinado. No se devuelve un valor
     * NULL, en su lugar lanzando una excepción, al no poder procesarse una
     * evidencia sin Person asociado.
     * 
     * @param saga Código saga por el que buscar
     * @return Person hallado en base de datos
     * @throws IllegalArgumentException No se ha podido encontrar la persona
     *                                  especificada
     */
    private Person getPersonBySaga(String saga) throws IllegalArgumentException {
        try {
            return people.get(people.indexOf(new Person(saga)));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("No existe persona con el código saga especificado.");
        }
    }

    /**
     * Obtener evidencia para una persona determinada. Se busca en el mapa de
     * evidencias en procesamiento, y se devuelve un Evidence vacío, con la persona
     * asociada, en caso de no encontrarse.
     * 
     * @param person Person por el que buscar
     * @return Evidence hallado o Evidence nuevo en caso de no hallarse
     */
    private Evidence getEvidenceForPerson(Person person) {
        Evidence evidence = evidences.get(person);
        return evidence != null ? evidence : new Evidence(person);
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
     * @param type Tipo de evidencia por el que buscar
     * @return EvidenceType hallado en base de datos
     * @throws IndexOutOfBoundsException No se ha podido encontrar el tipo
     *                                   especificado
     */
    private EvidenceType getEvidenceType(String type) throws IndexOutOfBoundsException {
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
    private Evidence setTypeForWeek(Evidence evidence, int week, EvidenceType evidenceType)
            throws IllegalArgumentException {
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
    public void clearReport(boolean deleteComments) {
        propertiesService.clear();
        if (deleteComments)
            evidenceCommentService.clear();
        clear();
        evidenceErrorService.clear();
    }

    /**
     * Almacenar datos de parámetros, evidencias, y errores.
     */
    private void saveReport() {
        propertiesService.saveAll(propertiesList);
        propertiesService.saveAll(weekProperties);
        saveAll(new ArrayList<>(evidences.values()));
        evidenceErrorService.saveAll(evidenceErrors);
    }

}
