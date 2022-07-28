package com.ccsw.gtemanager.config.security;

import org.springframework.security.core.Authentication;

import java.util.Date;

/**
 * Class to store user information which is later encapsulated into {@link Authentication} object.
 *
 */
public class UserInfoAppDto extends UserInfoDto {

   private String role;

   private String jwt;

   private Date expiration;

   /**
   * @return role
   */
   public String getRole() {

      return this.role;
   }

   /**
   * @param role new value of {@link #getRole}.
   */
   public void setRole(String role) {

      this.role = role;
   }

   /**
   * @return expiration
   */
   public Date getExpiration() {

      return this.expiration;
   }

   /**
   * @param expiration new value of {@link #getExpiration}.
   */
   public void setExpiration(Date expiration) {

      this.expiration = expiration;
   }

   /**
   * @return jwt
   */
   public String getJwt() {

      return this.jwt;
   }

   /**
   * @param jwt new value of {@link #getJwt}.
   */
   public void setJwt(String jwt) {

      this.jwt = jwt;
   }

}
