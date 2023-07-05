package com.ccsw.gtemanager.recurrence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ccsw.gtemanager.recurrence.model.Recurrence;
import com.ccsw.gtemanager.recurrence.model.RecurrenceDto;

@Service
public class RecurrenceServiceImpl implements RecurrenceService {

    @Autowired
    RecurrenceRepository recurrenceRepository;

    @Override
    @Transactional(readOnly = false)
    public void save(RecurrenceDto dto) {

        try {

            if (dto.getEnabled()) {
                Recurrence recurrence = new Recurrence();
                recurrence.setPersonId(dto.getPersonId());
                recurrenceRepository.save(recurrence);
            } else {
                recurrenceRepository.deleteByPersonId(dto.getPersonId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void clear() {
        recurrenceRepository.deleteAllInBatch();
    }

}
