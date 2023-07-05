package com.ccsw.gtemanager.evidenceview.model;

import com.ccsw.gtemanager.comment.model.CommentDto;
import com.ccsw.gtemanager.evidencetype.model.EvidenceTypeDto;
import com.ccsw.gtemanager.person.model.PersonDto;

public class EvidenceViewDto {

    private PersonDto person;

    private String saga;

    private CommentDto comment;

    private String manager;

    private String project;

    private String client;

    private Boolean recurrence;

    private String rowColor;

    private EvidenceTypeDto evidenceTypeW1;

    private EvidenceTypeDto evidenceTypeW2;

    private EvidenceTypeDto evidenceTypeW3;

    private EvidenceTypeDto evidenceTypeW4;

    private EvidenceTypeDto evidenceTypeW5;

    private EvidenceTypeDto evidenceTypeW6;

    private boolean emailNotificationSent;

    public PersonDto getPerson() {
        return person;
    }

    public void setPerson(PersonDto person) {
        this.person = person;
    }

    public String getSaga() {
        return saga;
    }

    public void setSaga(String saga) {
        this.saga = saga;
    }

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
        this.comment = comment;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

    public EvidenceTypeDto getEvidenceTypeW1() {
        return evidenceTypeW1;
    }

    public void setEvidenceTypeW1(EvidenceTypeDto evidenceTypeW1) {
        this.evidenceTypeW1 = evidenceTypeW1;
    }

    public EvidenceTypeDto getEvidenceTypeW2() {
        return evidenceTypeW2;
    }

    public void setEvidenceTypeW2(EvidenceTypeDto evidenceTypeW2) {
        this.evidenceTypeW2 = evidenceTypeW2;
    }

    public EvidenceTypeDto getEvidenceTypeW3() {
        return evidenceTypeW3;
    }

    public void setEvidenceTypeW3(EvidenceTypeDto evidenceTypeW3) {
        this.evidenceTypeW3 = evidenceTypeW3;
    }

    public EvidenceTypeDto getEvidenceTypeW4() {
        return evidenceTypeW4;
    }

    public void setEvidenceTypeW4(EvidenceTypeDto evidenceTypeW4) {
        this.evidenceTypeW4 = evidenceTypeW4;
    }

    public EvidenceTypeDto getEvidenceTypeW5() {
        return evidenceTypeW5;
    }

    public void setEvidenceTypeW5(EvidenceTypeDto evidenceTypeW5) {
        this.evidenceTypeW5 = evidenceTypeW5;
    }

    public EvidenceTypeDto getEvidenceTypeW6() {
        return evidenceTypeW6;
    }

    public void setEvidenceTypeW6(EvidenceTypeDto evidenceTypeW6) {
        this.evidenceTypeW6 = evidenceTypeW6;
    }

    public boolean isEmailNotificationSent() {
        return emailNotificationSent;
    }

    public void setEmailNotificationSent(boolean emailNotificationSent) {
        this.emailNotificationSent = emailNotificationSent;
    }

    /**
     * @return the client
     */
    public String getClient() {
        return client;
    }

    /**
     * @param client the client to set
     */
    public void setClient(String client) {
        this.client = client;
    }

    /**
     * @return the recurrence
     */
    public Boolean getRecurrence() {
        return recurrence;
    }

    /**
     * @param recurrence the recurrence to set
     */
    public void setRecurrence(Boolean recurrence) {
        this.recurrence = recurrence;
    }
}
