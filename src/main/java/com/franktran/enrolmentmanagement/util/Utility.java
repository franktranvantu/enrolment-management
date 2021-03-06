package com.franktran.enrolmentmanagement.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class Utility {

  public static final String DATE_FORMAT = "dd/MM/yyyy";

  public static String getSiteURL(HttpServletRequest request) {
    String siteURL = request.getRequestURL().toString();
    return siteURL.replace(request.getServletPath(), StringUtils.EMPTY);
  }
}
