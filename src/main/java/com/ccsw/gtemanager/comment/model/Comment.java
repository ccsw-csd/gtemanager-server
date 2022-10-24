package com.ccsw.gtemanager.comment.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ccsw.gtemanager.person.model.Person;

@Entity
@Table(name = "evidence_comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "last_edit_date", nullable = false)
    private Date lastEditDate;

    @Column(name = "last_edit_author", nullable = false)
    private String lastEditAuthor;

    public Long getId() {
        return id;
    }

    /**
     * Almacenar ID de EvidenceComment
     * 
     * @param id ID de EvidenceComment (Long)
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtener Person implicada en EvidenceComment
     * 
     * @return persona (Person)
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Almacenar Person implicada en EvidenceComment
     * 
     * @param person persona (Person)
     */
    public void setPerson(Person person) {
        this.person = person;
    }

    /**
     * Obtener comentario almacenado en EvidenceComment
     * 
     * @return comentario en formato String
     */
    public String getComment() {
        return comment;
    }

    /**
     * Almacenar comentario en EvidenceComment
     * 
     * @param comment comentario (String)
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Obtener fecha de la última edición en EvidenceComment
     * 
     * @return fecha de la última edición en formato Date
     */
    public Date getLastEditDate() {
        return lastEditDate;
    }

    /**
     * Almacenar fecha de la última edición en EvidenceComment
     * 
     * @param lastEditDate fecha (Date)
     */
    public void setLastEditDate(Date lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    /**
     * Obtener autor de la última edición en EvidenceComment
     * 
     * @return autor de la última edición en formato String
     */
    public String getLastEditAuthor() {
        return lastEditAuthor;
    }

    /**
     * Almacenar autor de la última edición en EvidenceComment
     * 
     * @param lastEditAuthor autor (String)
     */
    public void setLastEditAuthor(String lastEditAuthor) {
        this.lastEditAuthor = lastEditAuthor;
    }
}