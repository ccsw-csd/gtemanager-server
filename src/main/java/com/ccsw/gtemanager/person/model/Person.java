package com.ccsw.gtemanager.person.model;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.Table;

import com.ccsw.gtemanager.center.model.Center;

/**
 * Person: clase para la gestión de datos de persona. Contiene atributos para
 * código de saga, nombre de usuario, correo electrónico, nombre y apellidos,
 * centro de trabajo, código de negocio, grado, y actividad, además de getters y
 * setters. Se implementa EntityGraph para optimización de consultas.
 */
@NamedEntityGraph(name = "person-entity-graph", attributeNodes = { @NamedAttributeNode("center") })
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "saga", nullable = false)
    private String saga;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "center")
    private Center center;

    @Column(name = "businesscode")
    private String businessCode;

    @Column(name = "grade")
    private String grade;

    @Column(name = "active", nullable = false)
    private Boolean active;

    /**
     * Constructor vacío para la creación de Person
     */
    public Person() {

    }

    /**
     * Constructor con parámetro para asociar saga a Person
     * 
     * @param saga Código saga de persona
     */
    public Person(String saga) {
        this.saga = saga;
    }

    /**
     * Obtener ID de Person
     * 
     * @return ID en formato Long
     */
    public Long getId() {
        return id;
    }

    /**
     * Obtener código saga de Person
     * 
     * @return código saga en formato String
     */
    public String getSaga() {
        return saga;
    }

    /**
     * Obtener nombre de usuario de Person
     * 
     * @return nombre de usuario en formato String
     */
    public String getUsername() {
        return username;
    }

    /**
     * Obtener correo electrónico de Person
     * 
     * @return correo electrónico en formato String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtener nombre de Person
     * 
     * @return nombre en formato String
     */
    public String getName() {
        return name;
    }

    /**
     * Obtener appelidos de Person
     * 
     * @return apellidos en formato String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Obtener Center asociado a Person
     * 
     * @return centro de trabajo (Center)
     */
    public Center getCenter() {
        return center;
    }

    /**
     * Obtener código de negocio de Person
     * 
     * @return código en formato String
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * Obtener grado de Person
     * 
     * @return grado en formato String
     */
    public String getGrade() {
        return grade;
    }

    /**
     * Obtener actividad actual de Person
     * 
     * @return true si activo, false si inactivo
     */
    public Boolean isActive() {
        return active;
    }

    /**
     * Almacenar ID de Person
     * 
     * @param id ID de Person (Long)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Almacenar código saga de Person
     * 
     * @param saga código saga (String)
     */
    public void setSaga(String saga) {
        this.saga = saga;
    }

    /**
     * Almacenar nombre de usuario de Person
     * 
     * @param username nombre de usuario (String)
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Almacenar correo electrónico de Person
     * 
     * @param email correo electrónico (String)
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Almacenar nombre de Person
     * 
     * @param name nombre (String)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Almacenar apellidos de Person
     * 
     * @param lastName apellidos (String)
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Almacenar Center asociado a Person
     * 
     * @param center centro de trabajo (Center)
     */
    public void setCenter(Center center) {
        this.center = center;
    }

    /**
     * Almacenar código de negocio de Person
     * 
     * @param businessCode código (String)
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    /**
     * Almacenar grado de Person
     * 
     * @param grade grado (String)
     */
    public void setGrade(String grade) {
        this.grade = grade;
    }

    /**
     * Almacenar actividad de Person
     * 
     * @param active estado de actividad (Boolean)
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        return Objects.hash(saga);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Person other = (Person) obj;
        return Objects.equals(saga, other.saga);
    }

}
