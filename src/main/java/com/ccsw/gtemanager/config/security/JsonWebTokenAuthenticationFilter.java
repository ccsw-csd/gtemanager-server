package com.ccsw.gtemanager.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import javax.inject.Named;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * A pre-authenticated filter which purpose is only to extract the necessary
 * information on the principal from the incoming request, rather than to
 * authenticate them.
 *
 */
@Named
public class JsonWebTokenAuthenticationFilter extends RequestHeaderAuthenticationFilter {

  /**
   * ACCESS_HEADER_NAME
   */
  public static final String ACCESS_HEADER_NAME = "Authorization";

  /**
   * SecurityContext
   */
  public static final ThreadLocal<SecurityContext> SecurityContext = new ThreadLocal<>();

  /**
   * The constructor.
   */
  public JsonWebTokenAuthenticationFilter() {

    // Don't throw exceptions if the header is missing
    setExceptionIfHeaderMissing(false);

    // Set the name of the request header that contains the username
    setPrincipalRequestHeader(ACCESS_HEADER_NAME);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    JsonWebTokenAuthenticationFilter.SecurityContext.set(SecurityContextHolder.getContext());

    super.doFilter(request, response, chain);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Autowired
  public void setAuthenticationManager(AuthenticationManager authenticationManager) {

    super.setAuthenticationManager(authenticationManager);
  }

}
