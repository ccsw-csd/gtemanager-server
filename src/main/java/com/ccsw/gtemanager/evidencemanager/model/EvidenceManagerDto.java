package com.ccsw.gtemanager.evidencemanager.model;

public class EvidenceManagerDto {

    private String manager;

    private String project;

    public EvidenceManagerDto(String manager, String project) {
        this.manager = manager;
        this.project = project;
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
}
