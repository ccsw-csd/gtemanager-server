package com.ccsw.gtemanager.blacklist.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.person.model.Person;

/**
 * Evidence: clase para la gestión de datos de evidencia. Contiene atributos
 * para la persona implicada, además de los tipos de evidencia registrados en
 * las semanas de un periodo, y getters y setters. Se implementa EntityGraph
 * para optimización de consultas.
 */
@NamedEntityGraph(name = "blacklist-entity-graph", attributeNodes = { @NamedAttributeNode(value = "person", subgraph = "person-subgraph") }, subgraphs = {
        @NamedSubgraph(name = "person-subgraph", attributeNodes = { @NamedAttributeNode("center") }) })
@Entity
@Table(name = "person_recurrence")
public class Blacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "comment", nullable = true)
    private String comment;

    @Column(name = "manager", nullable = true)
    private String manager;

    @Column(name = "project", nullable = true)
    private String project;

    @Column(name = "client", nullable = true)
    private String client;

    @Column(name = "row_color", nullable = true)
    private String rowColor;

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRowColor() {
        return rowColor;
    }

    public void setRowColor(String rowColor) {
        this.rowColor = rowColor;
    }

}