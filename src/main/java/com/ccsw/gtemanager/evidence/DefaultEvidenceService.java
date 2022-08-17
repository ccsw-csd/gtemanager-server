package com.ccsw.gtemanager.evidence;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidence.model.EvidenceComment;
import com.ccsw.gtemanager.evidence.model.EvidenceError;
import com.ccsw.gtemanager.evidence.model.EvidenceType;
import com.ccsw.gtemanager.evidence.model.FormDataDto;

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

	private static DateTimeFormatter formatMonth = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

	private static DateTimeFormatter formatWeek = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd-MMM").toFormatter(Locale.getDefault());
	
	private static DateTimeFormatter formatDateTime = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendPattern("dd/MM/yyyy HH:mm").toFormatter(Locale.getDefault());

	@Override
	public void uploadEvidence(FormDataDto upload) throws IllegalArgumentException, IOException {

		Workbook gteEvidences = WorkbookFactory.create(upload.getFile().getInputStream());

		Sheet sheet = gteEvidences.getSheetAt(0);

		Cell cellFromDate = sheet.getRow(1).getCell(1);
		Cell cellToDate = sheet.getRow(2).getCell(1);
		Cell cellOperatingUnit = sheet.getRow(3).getCell(1);
		Cell cellProductionUnit = sheet.getRow(4).getCell(1);
		Cell cellSupervisorName = sheet.getRow(5).getCell(1);
		Cell cellEmployeeName = sheet.getRow(6).getCell(1);
		Cell cellBusinessUnit = sheet.getRow(7).getCell(1);
		Cell cellRunDate = sheet.getRow(9).getCell(1);

		Map<String, String> reportParams = new LinkedHashMap<String, String>();
		reportParams.put("fromDate", cellFromDate.getStringCellValue());
		reportParams.put("toDate", cellToDate.getStringCellValue());
		reportParams.put("operatingUnit", cellOperatingUnit.getStringCellValue());
		reportParams.put("productionUnit", cellProductionUnit.getStringCellValue());
		reportParams.put("supervisorName", cellSupervisorName.getStringCellValue());
		reportParams.put("employeeName", cellEmployeeName.getStringCellValue());
		reportParams.put("businessUnit", cellBusinessUnit.getStringCellValue());
		reportParams.put("runDate", cellRunDate.getStringCellValue());

		System.out.println(reportParams.toString());

		LocalDate fromDate = null;
		LocalDate toDate = null;
		try {
			fromDate = LocalDate.parse(cellFromDate.getStringCellValue(), formatMonth);
			toDate = LocalDate.parse(cellToDate.getStringCellValue(), formatMonth);

			if (fromDate.compareTo(toDate) > 0)
				throw new IllegalArgumentException(
						"El informe no se corresponde con un mes o periodo válido. [period]");
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("El informe no contiene fechas de periodo válidas (B2, C2). [period]");
		}

		gteEvidences.close();
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

	public String findWeekForDay(LocalDate day) throws IllegalArgumentException {
		String monday = day.with(DayOfWeek.MONDAY).format(formatWeek).toUpperCase();
		String sunday = day.with(DayOfWeek.SUNDAY).format(formatWeek).toUpperCase();

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
