package com.ccsw.gtemanager.email.model;

/**
 * TODO DOCS
 *
 */
public class EmailDto {
    private String to, subject, body, cc;

    public EmailDto(String to, String subject, String body, String cc) {
        this.to = to;
        this.subject = subject;
        this.body = body;
        this.cc = cc;
    }

    public String getTo() {
        return to;
    }

    public String getSubject() {
        return subject;
    }

    public String getBody() {
        return body;
    }

    public String getCc() {
        return cc;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

}