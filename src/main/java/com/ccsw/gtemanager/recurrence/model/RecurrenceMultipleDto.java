package com.ccsw.gtemanager.recurrence.model;

public class RecurrenceMultipleDto {

    private Long[] personId;
    private Boolean enabled;

    /**
     * @return the personId
     */
    public Long[] getPersonId() {
        return personId;
    }

    /**
     * @param personId the personId to set
     */
    public void setPersonId(Long[] personId) {
        this.personId = personId;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

}
