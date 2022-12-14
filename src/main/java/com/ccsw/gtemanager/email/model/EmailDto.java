package com.ccsw.gtemanager.email.model;

/**
 * EmailDto: DTO para datos de correos electrónicos a procesar y enviar.
 * Contiene atributos para destinatarios, asunto, y cuerpo del mensaje, además
 * de getters y setters.
 */
public class EmailDto {

    private String to;
    private String cc;
    private String subject;
    private String body;

    /**
     * Constructor con parámetros para la creación de un mensaje de correo
     * electrónico.
     * 
     * @param to      Dirección del destinatario
     * @param cc      Dirección de copia carbón
     * @param subject Asunto del mensaje
     * @param body    Cuerpo del mensaje
     */
    public EmailDto(String to, String cc, String subject, String body) {
        this.to = to;
        this.cc = cc;
        this.subject = subject;
        this.body = body;
    }

    /**
     * Obtener dirección del destinatario
     * 
     * @return Dirección en formato String
     */
    public String getTo() {
        return to;
    }

    /**
     * Obtener dirección de copia carbón
     * 
     * @return Dirección en formato String
     */
    public String getCc() {
        return cc;
    }

    /**
     * Obtener asunto del mensaje
     * 
     * @return Asunto en formato String
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Obtener cuerpo del mensaje
     * 
     * @return Mensaje en formato String
     */
    public String getBody() {
        return body;
    }

    /**
     * Almacenar dirección del destinatario
     * 
     * @param to Dirección (String)
     */
    public void setTo(String to) {
        this.to = to;
    }

    /**
     * Almacenar dirección de copia carbón
     * 
     * @param cc Dirección (String)
     */
    public void setCc(String cc) {
        this.cc = cc;
    }

    /**
     * Almacenar asunto del mensaje
     * 
     * @param subject Asunto (String)
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Almacenar cuerpo del mensaje
     * 
     * @param body Cuerpo (String)
     */
    public void setBody(String body) {
        this.body = body;
    }

}
