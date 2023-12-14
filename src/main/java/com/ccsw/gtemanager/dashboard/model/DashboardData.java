package com.ccsw.gtemanager.dashboard.model;

public class DashboardData {

    private String name;
    private Long weeks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeeks() {
        return weeks;
    }

    public void setWeeks(Long weeks) {
        this.weeks = weeks;
    }

    public Long getHours() {
        return weeks * 5 * 8;
    }

}
