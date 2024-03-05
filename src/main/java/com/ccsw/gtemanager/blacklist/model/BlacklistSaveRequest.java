package com.ccsw.gtemanager.blacklist.model;

public class BlacklistSaveRequest {

    private int day;

    private int year;

    private int month;

    private String comment;

    private Long[] persons;

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long[] getPersons() {
        return persons;
    }

    public void setPersons(Long[] persons) {
        this.persons = persons;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
