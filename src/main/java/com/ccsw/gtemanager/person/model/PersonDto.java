package com.ccsw.gtemanager.person.model;

import com.ccsw.gtemanager.evidence.model.Center;

public class PersonDto {
    private Long id;

    private String saga, username, email, name, lastName, businessCode, grade;

    private Center center;

    private boolean active;

    public Long getId() {
        return id;
    }

    public String getSaga() {
        return saga;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public Center getCenter() {
        return center;
    }

    public String getBusinessCode() {
        return businessCode;
    }

    public String getGrade() {
        return grade;
    }

    public boolean isActive() {
        return active;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSaga(String saga) {
        this.saga = saga;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCenter(Center center) {
        this.center = center;
    }

    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

}
