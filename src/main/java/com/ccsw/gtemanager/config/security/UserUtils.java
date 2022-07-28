package com.ccsw.gtemanager.config.security;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author mvallsal
 *
 */
public class UserUtils {

  /**
   * @return UserDetailsJWTDto
   */
  public static UserInfoAppDto getUserDetails() {

    return (UserInfoAppDto) SecurityContextHolder.getContext().getAuthentication().getDetails();
  }

}
