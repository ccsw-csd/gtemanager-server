package com.ccsw.gtemanager.dashboard.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "v_evidence_dashboard")
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "client", nullable = false)
    private String client;

    @Column(name = "evidence_weeks")
    private Long evidenceWeeks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public Long getEvidenceWeeks() {
        return evidenceWeeks;
    }

    public void setEvidenceWeeks(Long evidenceWeeks) {
        this.evidenceWeeks = evidenceWeeks;
    }

}