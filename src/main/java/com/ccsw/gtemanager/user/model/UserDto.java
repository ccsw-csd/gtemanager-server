package com.ccsw.gtemanager.user.model;

/**
 *
 * @author ccsw
 *
 */
public class UserDto {

    private Long id;

    private String username;

    private String email;

    private String firstName;

    private String lastName;

    /**
     * @return the id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return the username
     */
    public String getUsername() {

        return this.username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {

        this.username = username;
    }

    /**
     * @return the email
     */
    public String getEmail() {

        return this.email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {

        this.email = email;
    }

    /**
     * @return the first_name
     */
    public String getFirstName() {

        return this.firstName;
    }

    /**
     * @param firstName the first name to set
     */
    public void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * @return the last name
     */
    public String getLastName() {

        return this.lastName;
    }

    /**
     * @param lastName the last name to set
     */
    public void setLastName(String lastName) {

        this.lastName = lastName;
    }

}
