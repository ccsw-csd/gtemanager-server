package com.ccsw.gtemanager.evidencecolor;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.ccsw.gtemanager.evidencecolor.model.EvidenceColor;
import com.ccsw.gtemanager.person.PersonService;
import com.ccsw.gtemanager.person.model.Person;

@Service
@Transactional
public class EvidenceColorServiceImpl implements EvidenceColorService {

    @Autowired
    EvidenceColorRepository evidenceColorRepository;

    @Autowired
    PersonService personService;

    @Override
    public void modifyColor(Long personId, String rowColor) {

        EvidenceColor evidenceColor = evidenceColorRepository.getByPersonId(personId).orElse(null);

        if (StringUtils.hasText(rowColor) == false) {
            if (evidenceColor != null)
                evidenceColorRepository.delete(evidenceColor);

            return;
        }

        if (evidenceColor == null) {
            Person person = personService.findById(personId);
            evidenceColor = new EvidenceColor();
            evidenceColor.setPerson(person);
        }

        evidenceColor.setRowColor(rowColor);
        evidenceColorRepository.save(evidenceColor);
    }

    @Override
    public void clear(boolean clearGrey) {

        if (clearGrey)
            evidenceColorRepository.deleteAllInBatch();
        else
            evidenceColorRepository.removeAllWithoutGreyColor();
    }
}
