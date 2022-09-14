package com.ccsw.gtemanager.email;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ccsw.gtemanager.common.exception.BadRequestException;
import com.ccsw.gtemanager.email.model.ReminderDto;
import com.ccsw.gtemanager.evidence.EvidenceService;
import com.ccsw.gtemanager.evidence.model.Evidence;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.properties.PropertiesService;

/**
 * EmailServiceImpl: clase implementación de EmailService. Contiene métodos para
 * la composición y envío de emails de recordatorio.
 * 
 * TODO FIXME Esta versión de la clase contiene la implementación de envío de
 * mensajes a un servidor de correo externo. Eliminar comentarios con TODO,
 * configurar String con dirección de remitente, y descomentar llamada al método
 * para hacerlo funcionar. Servidor correo configurable en HOST y PORT.
 * "localhost:1025" para hacer pruebas a servidor local que simula un envío
 * (mailhog)
 */
@Service
@Transactional
public class EmailServiceImpl implements EmailService {

    private static final String REMINDER_TEMPLATE_PATH = "/src/main/resources/reminder-email-template.vm";

    private static final String REMINDER_TEMPLATE_PERIOD_PARAM = "period";
    private static final String REMINDER_TEMPLATE_CLOSING_DATE_PARAM = "closingDate";
    private static final String REMINDER_TEMPLATE_WEEKLIST_PARAM = "weeks";
    private static final String REMINDER_TEMPLATE_PERSON_PARAM = "person";
    private static final String REMINDER_TEMPLATE_TYPELIST_PARAM = "evidenceTypes";
    private static final String REMINDER_TEMPLATE_PENDING_PARAM = "pending";

    private static final String ENCODING = "UTF-8";
    private static final String PERIOD_SEPARATOR = " - ";

    // TODO Variables de configuración de sesión
    private static final boolean STARTTLS = true;
    private static final boolean AUTH = false;
    private static final String PROTOCOL = "TLSv1.2";
    private static final String SESSION_TRUST = "mail.smtp.ssl.trust";
    private static final String SESSION_PROTOCOL = "mail.smtp.ssl.protocols";
    private static final String SESSION_SMTP_PORT = "mail.smtp.port";
    private static final String SESSION_SMTP_HOST = "mail.smtp.host";
    private static final String SESSION_ENABLE_STARTTLS = "mail.smtp.starttls.enable";
    private static final String SESSION_ENABLE_AUTH = "mail.smtp.auth";
    // TODO content type, importancia del mensaje
    private static final String REMINDER_EMAIL_CONTENT_TYPE = "text/html; charset=utf-8";
    private static final String REMINDER_EMAIL_IMPORTANCE_KEY = "Importance";
    private static final String REMINDER_EMAIL_PRIORITY_KEY = "X-Priority";
    private static final String REMINDER_EMAIL_IMPORTANCE_VALUE = "High";
    private static final String REMINDER_EMAIL_PRIORITY_VALUE = "1";
    // TODO nombre del remitente, dirección del remitente, asunto
    private static final String REMINDER_EMAIL_NAME = "GTE Manager";
    private static final String REMINDER_EMAIL_ADDRESS = "[DIRECCIÓN REMITENTE AQUÍ]";
    private static final String REMINDER_EMAIL_SUBJECT = "GTE: Recordatorio ";
    // TODO dirección del servidor de correo SMTP y puerto
    private static final String SMTP_HOST = "localhost";
    private static final String SMTP_PORT = "1025";

    // TODO separador para nombre del destinatario
    private static final String NAME_SEPARATOR = ", ";

    private static DateTimeFormatter formatDateDB = new DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("dd/MM/yyyy").toFormatter(Locale.getDefault());

    @Autowired
    private PropertiesService propertiesService;

    @Autowired
    private EvidenceService evidenceService;

    @Override
    public boolean sendReminderEmails(ReminderDto reminder) throws ResponseStatusException {
        if (reminder.getClosingDate().isBefore(LocalDate.now()))
            throw new BadRequestException("La fecha de cierre no puede ser anterior a la actual.");

        List<Evidence> evidences = evidenceService.findByGeography(reminder.getCenterId());
        if (evidences.isEmpty())
            throw new BadRequestException("No hay evidencias para el centro seleccionado.");

        // TODO configurar propiedades
        Properties prop = new Properties();
        prop.put(SESSION_ENABLE_AUTH, AUTH);
        prop.put(SESSION_ENABLE_STARTTLS, STARTTLS);
        prop.put(SESSION_SMTP_HOST, SMTP_HOST);
        prop.put(SESSION_SMTP_PORT, SMTP_PORT);
        prop.put(SESSION_PROTOCOL, PROTOCOL);
        prop.put(SESSION_TRUST, SMTP_HOST);

        Session session = Session
                .getInstance(prop);/*
                                    * // TODO getInstance(Properties, Authenticator) para autenticación con
                                    * contraseña
                                    * 
                                    * , new Authenticator() {
                                    * 
                                    * @Override protected PasswordAuthentication getPasswordAuthentication() {
                                    * return new PasswordAuthentication("", ""); } });
                                    */

        // TODO descomentar para habilitar
        // return sendAllMessages(composeEmails(session, reminder.getClosingDate(),
        // evidences));
        return false;
    }

    /**
     * Componer listado de mensajes de correo electrónico (EmailDto) según la fecha
     * de cierre especificada y el centro asociado a las personas.
     * 
     * @param session     Session para envío de mensajes
     * @param closingDate Fecha de cierre de periodo
     * @param evidences   Listado de evidencias asociadas al centro
     * @return Listado de Message con los mensajes procesados
     */
    private List<Message> composeEmails(Session session, LocalDate closingDate, List<Evidence> evidences) {
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

        List<Message> messages = new ArrayList<>();

        for (Evidence evidence : evidences) {
            StringWriter writer = new StringWriter();

            context.put(REMINDER_TEMPLATE_PERSON_PARAM, evidence.getPerson().getName());
            Map<String, EvidenceType> evidenceTypes = evidenceService.getTypesForEvidence(evidence, weeks);
            context.put(REMINDER_TEMPLATE_TYPELIST_PARAM, evidenceTypes);
            context.put(REMINDER_TEMPLATE_PENDING_PARAM, evidenceTypes.size());

            template.merge(context, writer);

            // TODO crear y configurar mensaje
            Message message = new MimeMessage(session);
            try {
                // TODO rte
                message.setFrom(new InternetAddress(REMINDER_EMAIL_ADDRESS, REMINDER_EMAIL_NAME));
                // TODO destinatario
                message.setRecipient(Message.RecipientType.TO,
                        new InternetAddress(evidence.getPerson().getEmail(), String.valueOf(
                                evidence.getPerson().getLastName() + NAME_SEPARATOR + evidence.getPerson().getName())));

                // TODO asunto y prioridad
                message.setSubject(REMINDER_EMAIL_SUBJECT + period);
                message.setHeader(REMINDER_EMAIL_PRIORITY_KEY, REMINDER_EMAIL_PRIORITY_VALUE);
                message.setHeader(REMINDER_EMAIL_IMPORTANCE_KEY, REMINDER_EMAIL_IMPORTANCE_VALUE);

                // TODO cuerpo del mensaje y content type
                MimeBodyPart mimeBodyPart = new MimeBodyPart();
                mimeBodyPart.setContent(writer.toString(), REMINDER_EMAIL_CONTENT_TYPE);

                message.setContent(new MimeMultipart(mimeBodyPart));
                messages.add(message);
            } catch (UnsupportedEncodingException | MessagingException e) {
                e.printStackTrace();
            }
        }

        return messages;
    }

    /**
     * Realizar el envío de mensajes al servidor de correo.
     * 
     * @param emails Listado de mensajes a enviar
     * @return true si todos los mensajes se han enviado correctamente, false si han
     *         habido errores
     */
    private boolean sendAllMessages(List<Message> emails) {
        boolean ok = true;
        for (Message message : emails) {
            try {
                // TODO enviar a servidor SMTP a través de Transport
                // descomentar para habilitar envío
                // Transport.send(message);
            } catch (Exception e) {
                ok = false;
            }
        }
        return ok;
    }

}
