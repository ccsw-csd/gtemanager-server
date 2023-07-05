package com.ccsw.gtemanager.evidencemanager;

import static com.ccsw.gtemanager.evidence.EvidenceServiceImpl.ALLOWED_FORMATS;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.evidencemanager.model.EvidenceManager;
import com.ccsw.gtemanager.evidencemanager.model.EvidenceManagerDto;
import com.ccsw.gtemanager.person.model.Person;

@Service
@Transactional
public class EvidenceManagerServiceImpl implements EvidenceManagerService {

    private static final int EVIDENCE_MANAGER_SHEET = 0;

    private static final int ROW_EVIDENCE_MANAGER_START = 6;

    private static final int COL_EVIDENCE_MANAGER_PERSON_EMAIL = 2;
    private static final int COL_EVIDENCE_MANAGER_PROJECT = 9;
    private static final int COL_EVIDENCE_MANAGER_MANAGER = 19;
    private static final int COL_EVIDENCE_MANAGER_CLIENT = 21;

    @Autowired
    private EvidenceManagerRepository evidenceManagerRepository;

    @Autowired
    private EvidenceService evidenceService;

    @Override
    public Boolean uploadEvidenceManager(FormDataDto upload) throws ResponseStatusException {

        if (upload.getFile() == null)
            throw new UnsupportedMediaTypeException();

        if (!ALLOWED_FORMATS.contains(upload.getFile().getContentType()))
            throw new UnprocessableEntityException();

        Sheet sheet = obtainSheet(upload.getFile());

        Map<String, Person> personMap = evidenceService.createEmailPersonMap();

        Map<Person, Set<EvidenceManagerDto>> evidenceManagers = new LinkedHashMap<>();
        boolean evidenceManagerWithNoErrors = true;

        Row currentRow = sheet.getRow(ROW_EVIDENCE_MANAGER_START);

        for (int i = ROW_EVIDENCE_MANAGER_START + 1; currentRow != null; i++) {
            String email = currentRow.getCell(COL_EVIDENCE_MANAGER_PERSON_EMAIL).getStringCellValue();
            String project = currentRow.getCell(COL_EVIDENCE_MANAGER_PROJECT).getStringCellValue();
            String manager = currentRow.getCell(COL_EVIDENCE_MANAGER_MANAGER).getStringCellValue();
            String client = currentRow.getCell(COL_EVIDENCE_MANAGER_CLIENT).getStringCellValue();

            if (StringUtils.hasText(email) && StringUtils.hasText(manager) && StringUtils.hasText(project)) {

                email = email.toLowerCase();
                Person person = personMap.get(email);

                if (person != null) {
                    evidenceManagers.computeIfAbsent(person, s -> new LinkedHashSet<>());
                    Set<EvidenceManagerDto> managers = evidenceManagers.get(person);
                    managers.add(new EvidenceManagerDto(formatName(manager), project, client));
                } else {
                    evidenceManagerWithNoErrors = false;
                }
            }
            currentRow = sheet.getRow(i);
        }

        clear();
        saveAll(evidenceManagers);

        return evidenceManagerWithNoErrors;
    }

    private String formatName(String name) {

        return name.replace(",", "").replace("Mr. ", "").replace("Mrs. ", "");
    }

    private Sheet obtainSheet(MultipartFile file) throws BadRequestException {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            return workbook.getSheetAt(EVIDENCE_MANAGER_SHEET);
        } catch (Exception e) {
            throw new BadRequestException("Se ha producido un error leyendo el archivo. Compruebe la validez de los datos y que no se encuentra encriptado.");
        }
    }

    private void saveAll(Map<Person, Set<EvidenceManagerDto>> evidenceManagers) {

        List<EvidenceManager> entities = new ArrayList<>();

        evidenceManagers.forEach((key, value) -> {
            EvidenceManager manager = new EvidenceManager();
            manager.setPerson(key);
            manager.setManager(value.stream().map(EvidenceManagerDto::getManager).distinct().collect(Collectors.toList()).toString().replace("[", "").replace("]", ""));
            manager.setProject(value.stream().map(EvidenceManagerDto::getProject).distinct().collect(Collectors.toList()).toString().replace("[", "").replace("]", ""));
            manager.setClient(value.stream().map(EvidenceManagerDto::getClient).distinct().collect(Collectors.toList()).toString().replace("[", "").replace("]", ""));
            entities.add(manager);
        });

        evidenceManagerRepository.saveAll(entities);
    }

    @Override
    public void clear() {

        evidenceManagerRepository.deleteAllInBatch();
    }
}
