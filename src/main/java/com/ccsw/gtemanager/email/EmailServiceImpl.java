package com.ccsw.gtemanager.email;

import java.io.StringWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.email.model.EmailDto;
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.properties.PropertiesService;

/**
 * EmailServiceImpl: clase implementación de EmailService. Contiene métodos para
 * la composición y envío de emails de recordatorio.
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final String REMINDER_EMAIL_API_URL1 = "https://ccsw.cap";
    private static final String REMINDER_EMAIL_API_URL2 = "gemini.com";
    private static final String REMINDER_EMAIL_API_SERVICE_PATH = "/email/send";
    private static final String REMINDER_EMAIL_SUBJECT = "GTE: Recordatorio ";

    private static final String REMINDER_TEMPLATE_PATH = "/src/main/resources/reminder-email-template.vm";

    private static final String REMINDER_TEMPLATE_PERIOD_PARAM = "period";
    private static final String REMINDER_TEMPLATE_CLOSING_DATE_PARAM = "closingDate";
    private static final String REMINDER_TEMPLATE_WEEKLIST_PARAM = "weeks";
    private static final String REMINDER_TEMPLATE_PERSON_PARAM = "person";
    private static final String REMINDER_TEMPLATE_TYPELIST_PARAM = "evidenceTypes";
    private static final String REMINDER_TEMPLATE_PENDING_PARAM = "pending";

    private static final String ENCODING = "UTF-8";
    private static final String PERIOD_SEPARATOR = " - ";

    private static DateTimeFormatter formatDateDB = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd/MM/yyyy").toFormatter(Locale.getDefault());

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private EvidenceService evidenceService;

    @Override
    public boolean sendReminderEmails(LocalDate closingDate, Long centerId) throws ResponseStatusException {
        return sendAllMessages(REMINDER_EMAIL_API_URL1 + REMINDER_EMAIL_API_URL2 + REMINDER_EMAIL_API_SERVICE_PATH,
                composeEmails(closingDate, centerId));
    }

    /**
     * TODO DOCS
     * 
     * @param closingDate
     * @param centerId
     * @return
     */
    private List<EmailDto> composeEmails(LocalDate closingDate, Long centerId) {
        VelocityEngine velocityEngine = new VelocityEngine();
        velocityEngine.init();

        Template template = velocityEngine.getTemplate(REMINDER_TEMPLATE_PATH, ENCODING);

        VelocityContext context = new VelocityContext();

        List<String> weeks = propertiesService.getWeeks();

        String period = weeks.get(0).split(PERIOD_SEPARATOR)[0] + PERIOD_SEPARATOR
                + weeks.get(weeks.size() - 1).split(PERIOD_SEPARATOR)[1];

        context.put(REMINDER_TEMPLATE_PERIOD_PARAM, period);
        context.put(REMINDER_TEMPLATE_CLOSING_DATE_PARAM, closingDate.format(formatDateDB));
        context.put(REMINDER_TEMPLATE_WEEKLIST_PARAM, weeks);

        List<Evidence> evidences = evidenceService.getEvidencesByCenter(centerId);

        List<EmailDto> messages = new ArrayList<>();

        for (Evidence evidence : evidences) {
            StringWriter writer = new StringWriter();

            context.put(REMINDER_TEMPLATE_PERSON_PARAM, evidence.getPersonId().getName());
            Map<String, EvidenceType> evidenceTypes = evidenceService.getTypesForEvidence(evidence, weeks);
            context.put(REMINDER_TEMPLATE_TYPELIST_PARAM, evidenceTypes);
            context.put(REMINDER_TEMPLATE_PENDING_PARAM, evidenceTypes.size());

            template.merge(context, writer);

            messages.add(new EmailDto(evidence.getPersonId().getEmail(), REMINDER_EMAIL_SUBJECT + period,
                    writer.toString(), null));
        }

        return messages;
    }

    /**
     * TODO DOCS
     * 
     * @param emailApiUrl
     * @param emails
     * @return
     */
    private boolean sendAllMessages(String emailApiUrl, List<EmailDto> emails) {
        boolean ok = true;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<?> response;
        for (EmailDto message : emails) {
            try {
                response = restTemplate.exchange(emailApiUrl, HttpMethod.POST,
                        new HttpEntity<EmailDto>(
                                new EmailDto(message.getTo(), message.getSubject(), message.getBody(), null)),
                        String.class);

                System.out.println(response.getStatusCodeValue());
            } catch (Exception e) {
                e.printStackTrace();
                ok = false;
            }
        }
        return ok;
    }

}
