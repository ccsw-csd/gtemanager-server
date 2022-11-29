package com.ccsw.gtemanager.properties;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.gtemanager.config.security.UserUtils;
import com.ccsw.gtemanager.properties.model.Properties;

/**
 * PropertiesServiceImpl: clase de implementación de PropertiesService
 */
@Service
@Transactional
public class PropertiesServiceImpl implements PropertiesService {

    private static final String PROPERTY_LOAD_DATE = "LOAD_DATE";
    private static final String PROPERTY_LOAD_USERNAME = "LOAD_USERNAME";
    private static final String PROPERTY_LOAD_WEEKS = "LOAD_WEEKS";
    private static final String PROPERTY_WEEK = "WEEK_";

    private static final int WEEK_PROPERTIES_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;

    private static final String PERIOD_SEPARATOR = " - ";

    @Autowired
    private PropertiesRepository propertiesRepository;

    private static DateTimeFormatter formatDate = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd-MMM-yyyy").toFormatter(Locale.getDefault());

    private static DateTimeFormatter formatDateDB = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd/MM/yyyy").toFormatter(Locale.getDefault());

    private static DateTimeFormatter formatDateTimeDB = new DateTimeFormatterBuilder().parseCaseInsensitive().appendPattern("dd/MM/yyyy HH:mm").toFormatter(Locale.getDefault());

    @Override
    public List<Properties> getProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public List<String> getWeeks() {
        List<String> weeks = new ArrayList<>();
        for (int i = WEEK_PROPERTIES_START; i <= MAX_WEEKS_IN_MONTH; i++) {
            Properties p = getProperty(PROPERTY_WEEK + i);
            if (p.getValue() != null) {
                String[] week = p.getValue().split(PERIOD_SEPARATOR);
                LocalDate firstDay = LocalDate.parse(week[0], formatDate);
                LocalDate lastDay = LocalDate.parse(week[1], formatDate);
                weeks.add(firstDay.format(formatDateDB) + PERIOD_SEPARATOR + lastDay.format(formatDateDB));
            }
        }
        return weeks;
    }

    @Override
    public Properties getProperty(String key) {
        return propertiesRepository.findByKey(key);
    }

    @Override
    public List<Properties> parseProperties(LocalDateTime runDate, List<String> weeks) throws DateTimeException {
        List<Properties> propertiesList = new ArrayList<>();
        propertiesList.add(new Properties(PROPERTY_LOAD_DATE, runDate.format(formatDateTimeDB)));

        propertiesList.add(new Properties(PROPERTY_LOAD_USERNAME, UserUtils.getUserDetails().getUsername()));

        List<Properties> weekProperties = new ArrayList<>();
        for (int i = WEEK_PROPERTIES_START; i <= MAX_WEEKS_IN_MONTH; i++) {
            Properties weekProperty = new Properties(PROPERTY_WEEK + i, null);
            try {
                weekProperty.setValue(weeks.get(i - 1));
            } catch (IndexOutOfBoundsException e) {
                weekProperty.setValue(null);
            }
            weekProperties.add(weekProperty);
        }

        propertiesList.add(new Properties(PROPERTY_LOAD_WEEKS, String.valueOf(weeks.size())));
        propertiesList.addAll(weekProperties);

        return propertiesList;
    }

    @Override
    public void saveAll(List<Properties> propertiesList) {

        for (Properties propertyDto : propertiesList) {

            Properties property = this.getProperty(propertyDto.getKey());
            if (property != null) {

                property.setValue(propertyDto.getValue());
                propertiesRepository.save(property);
            }
        }

    }

    @Override
    public void clear() {
        propertiesRepository.deleteAllInBatch();
    }
}