package com.ccsw.gtemanager.evidencemanager;

import com.ccsw.gtemanager.common.exception.BadRequestException;
import com.ccsw.gtemanager.common.exception.UnprocessableEntityException;
import com.ccsw.gtemanager.common.exception.UnsupportedMediaTypeException;
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidence.PersonSagaMapperRepository;
import com.ccsw.gtemanager.evidence.model.FormDataDto;
import com.ccsw.gtemanager.evidencemanager.model.EvidenceManager;
import com.ccsw.gtemanager.person.PersonService;
import com.ccsw.gtemanager.person.model.Person;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

import static com.ccsw.gtemanager.evidence.EvidenceServiceImpl.ALLOWED_FORMATS;

@Service
@Transactional
public class EvidenceManagerServiceImpl implements EvidenceManagerService {

    private static final int EVIDENCE_MANAGER_SHEET = 0;

    private static final int ROW_EVIDENCE_MANAGER_START = 6;

    private static final int COL_EVIDENCE_MANAGER_SAGA = 1;
    private static final int COL_EVIDENCE_MANAGER_MANAGER = 19;

    @Autowired
    private EvidenceManagerRepository evidenceManagerRepository;

    @Autowired
    private EvidenceService evidenceService;

    @Autowired
    private PersonService personService;

    @Autowired
    private PersonSagaMapperRepository personSagaMapperRepository;

    @Override
    public Boolean uploadEvidenceManager(FormDataDto upload) throws ResponseStatusException {

        if (upload.getFile() == null)
            throw new UnsupportedMediaTypeException();

        if (!ALLOWED_FORMATS.contains(upload.getFile().getContentType()))
            throw new UnprocessableEntityException();

        Sheet sheet = obtainSheet(upload.getFile());

        Map<String, Person> personMap = evidenceService.createSagaPersonMap();

        Map<Person, Set<String>> evidenceManagers = new LinkedHashMap<>();
        boolean evidenceManagerErrors = false;

        Row currentRow = sheet.getRow(ROW_EVIDENCE_MANAGER_START);

        for (int i = ROW_EVIDENCE_MANAGER_START + 1 ; currentRow != null; i++) {
            String saga = currentRow.getCell(COL_EVIDENCE_MANAGER_SAGA).getStringCellValue();
            String manager = currentRow.getCell(COL_EVIDENCE_MANAGER_MANAGER).getStringCellValue();

            if (StringUtils.hasText(saga) && StringUtils.hasText(manager)) {

                Person person = personMap.get(personService.parseSaga(saga));

                if (person != null) {
                    evidenceManagers.computeIfAbsent(person, s -> new LinkedHashSet<>());
                    Set<String> managers = evidenceManagers.get(person);
                    managers.add(formatName(manager));
                } else {
                    evidenceManagerErrors = true;
                }
            }
            currentRow = sheet.getRow(i);
        }

        clear();
        saveAll(evidenceManagers);

        return evidenceManagerErrors;
    }

    private String formatName(String name){

        return name.replace(",", "").replace("Mr. ", "").replace("Mrs. ", "");
    }

    private Sheet obtainSheet(MultipartFile file) throws BadRequestException {
        try (Workbook workbook = WorkbookFactory.create(file.getInputStream())) {
            return workbook.getSheetAt(EVIDENCE_MANAGER_SHEET);
        } catch (Exception e) {
            throw new BadRequestException("Se ha producido un error leyendo el archivo. Compruebe la validez de los datos y que no se encuentra encriptado.");
        }
    }

    private void saveAll(Map<Person, Set<String>> evidenceManagers){

        List<EvidenceManager> entities = new ArrayList<>();

        evidenceManagers.forEach((key, value) -> {
            EvidenceManager manager = new EvidenceManager();
            manager.setPerson(key);
            manager.setManager(value.toString().replace("[", "").replace("]", ""));
            entities.add(manager);
        });

        evidenceManagerRepository.saveAll(entities);
    }

    @Override
    public void clear() {

        evidenceManagerRepository.deleteAllInBatch();
    }
}
