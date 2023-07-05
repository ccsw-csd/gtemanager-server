package com.ccsw.gtemanager.recurrence;

import com.ccsw.gtemanager.recurrence.model.RecurrenceDto;

public interface RecurrenceService {

    void save(RecurrenceDto dto);

    void clear();

}
