package com.ccsw.gtemanager.dashboard.model;

import java.util.List;

public class DashboardResponse {

    private List<DashboardData> clients;
    private List<DashboardData> persons;

    public List<DashboardData> getClients() {
        return clients;
    }

    public void setClients(List<DashboardData> clients) {
        this.clients = clients;
    }

    public List<DashboardData> getPersons() {
        return persons;
    }

    public void setPersons(List<DashboardData> persons) {
        this.persons = persons;
    }

}
