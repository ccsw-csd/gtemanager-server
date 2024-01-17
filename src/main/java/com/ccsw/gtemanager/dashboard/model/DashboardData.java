package com.ccsw.gtemanager.dashboard.model;

public class DashboardData {

    private String name;
    private Long weeks;
    private Long countItems;

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

    public Long getCountItems() {
        return countItems;
    }

    public void setCountItems(Long countItems) {
        this.countItems = countItems;
    }

}
