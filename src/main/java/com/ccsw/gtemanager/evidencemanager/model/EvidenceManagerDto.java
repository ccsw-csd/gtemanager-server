package com.ccsw.gtemanager.evidencemanager.model;

public class EvidenceManagerDto {

    private String manager;

    private String project;

    private String client;

    public EvidenceManagerDto(String manager, String project, String client) {
        this.manager = manager;
        this.project = project;
        this.client = client;
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
}
