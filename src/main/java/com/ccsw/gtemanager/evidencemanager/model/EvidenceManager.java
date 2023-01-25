package com.ccsw.gtemanager.evidencemanager.model;

import com.ccsw.gtemanager.person.model.Person;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "evidence_manager")
public class EvidenceManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "manager", nullable = false)
    private String manager;

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

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

}