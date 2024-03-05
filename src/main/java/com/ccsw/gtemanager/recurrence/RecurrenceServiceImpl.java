package com.ccsw.gtemanager.recurrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.gtemanager.recurrence.model.Recurrence;
import com.ccsw.gtemanager.recurrence.model.RecurrenceDto;
import com.ccsw.gtemanager.recurrence.model.RecurrenceMultipleDto;

@Service
public class RecurrenceServiceImpl implements RecurrenceService {

    @Autowired
    RecurrenceRepository recurrenceRepository;

    @Override
    @Transactional(readOnly = false)
    public void save(RecurrenceDto dto) {

        try {
            saveOrDelete(dto.getPersonId(), dto.getEnabled());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void clear() {
        recurrenceRepository.deleteAllInBatch();
    }

    private void saveOrDelete(Long idPerson, boolean enabled) throws Exception {

        if (enabled) {
            Recurrence recurrence = new Recurrence();
            recurrence.setPersonId(idPerson);
            recurrenceRepository.save(recurrence);
        } else {
            recurrenceRepository.deleteByPersonId(idPerson);
        }

    }

    @Override
    @Transactional(readOnly = false)
    public void saveMultiple(RecurrenceMultipleDto dto) {

        for (Long id : dto.getPersonId()) {
            try {
                saveOrDelete(id, dto.getEnabled());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

}
