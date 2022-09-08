package com.ccsw.gtemanager.properties;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.properties.model.Properties;

/**
 * PropertiesServiceImpl: clase de implementación de PropertiesService
 */
@Service
@Transactional
public class PropertiesServiceImpl implements PropertiesService {

    private static final int WEEK_PROPERTIES_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;

    private static final String PERIOD_SEPARATOR = " - ";

    @Autowired
    private PropertiesRepository propertiesRepository;

    private static DateTimeFormatter formatDate = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

    private static DateTimeFormatter formatDateDB = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd/MM/yyyy").toFormatter(Locale.getDefault());

    @Override
    public List<Properties> getProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public List<String> getWeeks() {
        List<String> weeks = new ArrayList<>();
        for (int i = WEEK_PROPERTIES_START; i <= MAX_WEEKS_IN_MONTH; i++) {
            Properties p = getProperty("WEEK_" + i);
            if (p.getValue() != null)
                weeks.add(LocalDate.parse(p.getValue().split(PERIOD_SEPARATOR)[0], formatDate).format(formatDateDB)
                        + PERIOD_SEPARATOR
                        + LocalDate.parse(p.getValue().split(PERIOD_SEPARATOR)[1], formatDate).format(formatDateDB));
        }
        return weeks;
    }

    @Override
    public Properties getProperty(String key) {
        return propertiesRepository.findByKey(key);
    }

    @Override
    public void saveAll(List<Properties> propertiesList) {
        propertiesRepository.saveAll(propertiesList);
    }

    @Override
    public void clear() {
        propertiesRepository.deleteAllInBatch();
    }

}
