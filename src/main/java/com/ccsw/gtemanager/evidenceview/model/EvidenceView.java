package com.ccsw.gtemanager.evidenceview.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import org.springframework.data.annotation.Immutable;

import com.ccsw.gtemanager.comment.model.Comment;
import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "v_evidence_with_comment")
@Immutable
@NamedEntityGraph(name = "evidence-view-graph", attributeNodes = { @NamedAttributeNode("person"), @NamedAttributeNode("comment"), @NamedAttributeNode("evidenceTypeW1"), @NamedAttributeNode("evidenceTypeW2"),
        @NamedAttributeNode("evidenceTypeW3"), @NamedAttributeNode("evidenceTypeW4"), @NamedAttributeNode("evidenceTypeW5"), @NamedAttributeNode("evidenceTypeW6"), @NamedAttributeNode("emailNotificationSent") })
public class EvidenceView {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "saga")
    private String saga;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = true)
    private Comment comment;

    @Column(name = "manager")
    private String manager;

    @Column(name = "project")
    private String project;

    @Column(name = "client")
    private String client;

    @Column(name = "recurrence")
    private Boolean recurrence;

    @Column(name = "row_color")
    private String rowColor;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w1", nullable = true)
    private EvidenceType evidenceTypeW1;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w2", nullable = true)
    private EvidenceType evidenceTypeW2;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w3", nullable = true)
    private EvidenceType evidenceTypeW3;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w4", nullable = true)
    private EvidenceType evidenceTypeW4;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w5", nullable = true)
    private EvidenceType evidenceTypeW5;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w6", nullable = true)
    private EvidenceType evidenceTypeW6;

    @JoinColumn(name = "email_notification_sent", nullable = true)
    private boolean emailNotificationSent;

    public Person getPerson() {
        return person;
    }

    public String getSaga() {
        return saga;
    }

    public Comment getComment() {
        return comment;
    }

    public String getManager() {
        return manager;
    }

    public String getProject() {
        return project;
    }

    public String getRowColor() {
        return rowColor;
    }

    public EvidenceType getEvidenceTypeW1() {
        return evidenceTypeW1;
    }

    public EvidenceType getEvidenceTypeW2() {
        return evidenceTypeW2;
    }

    public EvidenceType getEvidenceTypeW3() {
        return evidenceTypeW3;
    }

    public EvidenceType getEvidenceTypeW4() {
        return evidenceTypeW4;
    }

    public EvidenceType getEvidenceTypeW5() {
        return evidenceTypeW5;
    }

    public EvidenceType getEvidenceTypeW6() {
        return evidenceTypeW6;
    }

    public boolean getEmailNotificationSent() {
        return emailNotificationSent;
    }

    public String getClient() {
        return client;
    }

    public Boolean getRecurrence() {
        return recurrence;
    }

}