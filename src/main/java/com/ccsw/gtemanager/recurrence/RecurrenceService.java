package com.ccsw.gtemanager.recurrence;

import com.ccsw.gtemanager.recurrence.model.RecurrenceDto;
import com.ccsw.gtemanager.recurrence.model.RecurrenceMultipleDto;

public interface RecurrenceService {

    void save(RecurrenceDto dto);

    void clear();

    void saveMultiple(RecurrenceMultipleDto dto);

}
