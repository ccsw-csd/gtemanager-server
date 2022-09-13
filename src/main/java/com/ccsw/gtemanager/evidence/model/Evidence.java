package com.ccsw.gtemanager.evidence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.evidencetype.model.EvidenceType;
import com.ccsw.gtemanager.person.model.Person;

/**
 * Evidence: clase para la gestión de datos de evidencia. Contiene atributos
 * para la persona implicada, además de los tipos de evidencia registrados en
 * las semanas de un periodo, y getters y setters. Se implementa EntityGraph
 * para optimización de consultas.
 */
@NamedEntityGraph(name = "evidence-entity-graph", attributeNodes = {
        @NamedAttributeNode(value = "person", subgraph = "person-subgraph"), @NamedAttributeNode("evidenceTypeW1"),
        @NamedAttributeNode("evidenceTypeW2"), @NamedAttributeNode("evidenceTypeW3"),
        @NamedAttributeNode("evidenceTypeW4"), @NamedAttributeNode("evidenceTypeW5"),
        @NamedAttributeNode("evidenceTypeW6") }, subgraphs = {
                @NamedSubgraph(name = "person-subgraph", attributeNodes = { @NamedAttributeNode("center") }) })
@Entity
@Table(name = "evidence")
public class Evidence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w1")
    private EvidenceType evidenceTypeW1;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w2")
    private EvidenceType evidenceTypeW2;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w3")
    private EvidenceType evidenceTypeW3;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w4")
    private EvidenceType evidenceTypeW4;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w5")
    private EvidenceType evidenceTypeW5;

    @ManyToOne
    @JoinColumn(name = "evidente_type_w6")
    private EvidenceType evidenceTypeW6;

    /**
     * Constructor vacío para la creación de Evidence
     */
    public Evidence() {

    }

    /**
     * Constructor con parámetro para asociar Person a Evidence
     * 
     * @param person Person implicada en Evidence
     */
    public Evidence(Person person) {
        this.person = person;
    }

    /**
     * Obtener ID de Evidence
     * 
     * @return ID en formato Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtener Person implicada en Evidence
     * 
     * @return persona (Person)
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Obtener tipo de evidencia almacenado en semana 1 del periodo
     * 
     * @return EvidenceType si existe, NULL si no ha sido asignado
     */
    public EvidenceType getEvidenceTypeW1() {
        return evidenceTypeW1;
    }

    /**
     * Obtener tipo de evidencia almacenado en semana 2 del periodo
     * 
     * @return EvidenceType si existe, NULL si no ha sido asignado
     */
    public EvidenceType getEvidenceTypeW2() {
        return evidenceTypeW2;
    }

    /**
     * Obtener tipo de evidencia almacenado en semana 3 del periodo
     * 
     * @return EvidenceType si existe, NULL si no ha sido asignado
     */
    public EvidenceType getEvidenceTypeW3() {
        return evidenceTypeW3;
    }

    /**
     * Obtener tipo de evidencia almacenado en semana 4 del periodo
     * 
     * @return EvidenceType si existe, NULL si no ha sido asignado
     */
    public EvidenceType getEvidenceTypeW4() {
        return evidenceTypeW4;
    }

    /**
     * Obtener tipo de evidencia almacenado en semana 5 del periodo
     * 
     * @return EvidenceType si existe, NULL si no ha sido asignado
     */
    public EvidenceType getEvidenceTypeW5() {
        return evidenceTypeW5;
    }

    /**
     * Obtener tipo de evidencia almacenado en semana 6 del periodo
     * 
     * @return EvidenceType si existe, NULL si no ha sido asignado
     */
    public EvidenceType getEvidenceTypeW6() {
        return evidenceTypeW6;
    }

    /**
     * Almacenar ID de Evidence
     * 
     * @param id ID de Evidence (Long)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Almacenar Person implicada en Evidence
     * 
     * @param person persona (Person)
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Almacenar EvidenceType correspondiente a la semana 1 del periodo
     * 
     * @param evidenceTypeW1 tipo de evidencia (EvidenceType)
     */
    public void setEvidenceTypeW1(EvidenceType evidenceTypeW1) {
        this.evidenceTypeW1 = evidenceTypeW1;
    }

    /**
     * Almacenar EvidenceType correspondiente a la semana 2 del periodo
     * 
     * @param evidenceTypeW2 tipo de evidencia (EvidenceType)
     */
    public void setEvidenceTypeW2(EvidenceType evidenceTypeW2) {
        this.evidenceTypeW2 = evidenceTypeW2;
    }

    /**
     * Almacenar EvidenceType correspondiente a la semana 3 del periodo
     * 
     * @param evidenceTypeW3 tipo de evidencia (EvidenceType)
     */
    public void setEvidenceTypeW3(EvidenceType evidenceTypeW3) {
        this.evidenceTypeW3 = evidenceTypeW3;
    }

    /**
     * Almacenar EvidenceType correspondiente a la semana 4 del periodo
     * 
     * @param evidenceTypeW4 tipo de evidencia (EvidenceType)
     */
    public void setEvidenceTypeW4(EvidenceType evidenceTypeW4) {
        this.evidenceTypeW4 = evidenceTypeW4;
    }

    /**
     * Almacenar EvidenceType correspondiente a la semana 5 del periodo
     * 
     * @param evidenceTypeW5 tipo de evidencia (EvidenceType)
     */
    public void setEvidenceTypeW5(EvidenceType evidenceTypeW5) {
        this.evidenceTypeW5 = evidenceTypeW5;
    }

    /**
     * Almacenar EvidenceType correspondiente a la semana 6 del periodo
     * 
     * @param evidenceTypeW6 tipo de evidencia (EvidenceType)
     */
    public void setEvidenceTypeW6(EvidenceType evidenceTypeW6) {
        this.evidenceTypeW6 = evidenceTypeW6;
    }
}