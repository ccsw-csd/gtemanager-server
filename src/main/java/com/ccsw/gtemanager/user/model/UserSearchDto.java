package com.ccsw.gtemanager.user.model;

import org.springframework.data.domain.Pageable;

public class UserSearchDto {

    String username;

    String name;

    private Pageable pageable;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name new value of {@link #getName}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username new value of {@link #getUsername}.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public Pageable getPageable() {
        return pageable;
    }

    /**
     * @param pageable new value of {@link #getPageable}.
     */
    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
