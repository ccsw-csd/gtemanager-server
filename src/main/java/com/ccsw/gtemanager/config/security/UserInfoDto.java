package com.ccsw.gtemanager.config.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pajimene
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDto {

  @JsonProperty("_id")
  private Long id;

  @JsonProperty("_rev")
  private String rev;

  private String realm;

  private String username;

  private List<String> mail;

  private List<String> displayName;

  @JsonProperty("sn")
  private List<String> lastName;

  @JsonProperty("givenName")
  private List<String> firstName;

  private List<String> employeeNumber;

  @JsonProperty("capgemini-GlobalID")
  private List<String> capgeminiGlobalID;

  private List<String> physicalDeliveryOfficeName;

  @JsonProperty("capgemini-Grade")
  private List<String> grade;

  /**
   * @return id
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @param id new value of {@link #getid}.
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @return rev
   */
  public String getRev() {

    return this.rev;
  }

  /**
   * @param rev new value of {@link #getrev}.
   */
  public void setRev(String rev) {

    this.rev = rev;
  }

  /**
   * @return realm
   */
  public String getRealm() {

    return this.realm;
  }

  /**
   * @param realm new value of {@link #getrealm}.
   */
  public void setRealm(String realm) {

    this.realm = realm;
  }

  /**
   * @return username
   */
  public String getUsername() {

    return this.username;
  }

  /**
   * @param username new value of {@link #getusername}.
   */
  public void setUsername(String username) {

    this.username = username;
  }

  private List<String> putFirstElement(List<String> list, String value) {

    if (list == null)
      list = new ArrayList<>();
    if (list.size() > 0)
      list.clear();

    list.add(value);

    return list;
  }

  private String getFirstElement(List<String> list) {

    if (list == null)
      return null;

    if (list.size() == 0)
      return null;

    return list.get(0);
  }

  /**
   * @return mail
   */
  public String getMail() {

    return getFirstElement(this.mail);
  }

  /**
   * @param mail new value of {@link #getmail}.
   */
  public void setMail(List<String> mail) {

    this.mail = mail;
  }

  /**
   * @return displayName
   */
  public String getDisplayName() {

    return getFirstElement(this.displayName);
  }

  /**
   * @param displayName new value of {@link #getdisplayName}.
   */
  public void setDisplayName(List<String> displayName) {

    this.displayName = displayName;
  }

  /**
   * @return lastName
   */
  public String getLastName() {

    return getFirstElement(this.lastName);
  }

  /**
   * @param lastName new value of {@link #getlastName}.
   */
  public void setLastName(List<String> lastName) {

    this.lastName = lastName;
  }

  /**
   * @return firstName
   */
  public String getFirstName() {

    return getFirstElement(this.firstName);
  }

  /**
   * @param firstName new value of {@link #getfirstName}.
   */
  public void setFirstName(List<String> firstName) {

    this.firstName = firstName;
  }

  /**
   * @return employeeNumber
   */
  public String getEmployeeNumber() {

    return getFirstElement(this.employeeNumber);
  }

  /**
   * @param employeeNumber new value of {@link #getemployeeNumber}.
   */
  public void setEmployeeNumber(List<String> employeeNumber) {

    this.employeeNumber = employeeNumber;
  }

  /**
   * @return capgeminiGlobalID
   */
  public String getCapgeminiGlobalID() {

    return getFirstElement(this.capgeminiGlobalID);
  }

  /**
   * @param capgeminiGlobalID new value of {@link #getcapgeminiGlobalID}.
   */
  public void setCapgeminiGlobalID(List<String> capgeminiGlobalID) {

    this.capgeminiGlobalID = capgeminiGlobalID;
  }

  /**
   * @return physicalDeliveryOfficeName
   */
  public String getPhysicalDeliveryOfficeName() {

    return getFirstElement(this.physicalDeliveryOfficeName);
  }

  /**
   * @param physicalDeliveryOfficeName new value of {@link #getphysicalDeliveryOfficeName}.
   */
  public void setPhysicalDeliveryOfficeName(List<String> physicalDeliveryOfficeName) {

    this.physicalDeliveryOfficeName = physicalDeliveryOfficeName;
  }

  /**
   * @return grade
   */
  public String getGrade() {

    return getFirstElement(this.grade);
  }

  /**
   * @param grade new value of {@link #getgrade}.
   */
  public void setGrade(List<String> grade) {

    this.grade = grade;
  }

  /**
   * @param value
   */
  public void setFirstNameValue(String value) {

    this.firstName = putFirstElement(this.firstName, value);

  }

  /**
   * @param value
   */
  public void setLastNameValue(String value) {

    this.lastName = putFirstElement(this.lastName, value);

  }

  /**
   * @param value
   */
  public void setDisplayNameValue(String value) {

    this.displayName = putFirstElement(this.displayName, value);

  }

  /**
   * @param value
   */
  public void setMailValue(String value) {

    this.mail = putFirstElement(this.mail, value);

  }

  /**
   * @param value
   */
  public void setEmployeeNumberValue(String value) {

    this.employeeNumber = putFirstElement(this.employeeNumber, value);

  }

  /**
   * @param value
   */
  public void setCapgeminiGlobalIDValue(String value) {

    this.capgeminiGlobalID = putFirstElement(this.capgeminiGlobalID, value);

  }

  /**
   * @param value
   */
  public void setGradeValue(String value) {

    this.grade = putFirstElement(this.grade, value);

  }

  /**
   * @param value
   */
  public void setPhysicalDeliveryOfficeNameValue(String value) {

    this.physicalDeliveryOfficeName = putFirstElement(this.physicalDeliveryOfficeName, value);

  }

}
