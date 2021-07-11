package com.franktran.enrolmentmanagement.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.franktran.enrolmentmanagement.util.Utility.DATE_FORMAT;

public class ValidBirthDateValidator implements ConstraintValidator<ValidBirthDate, String> {

  @Override
  public boolean isValid(String dateStr, ConstraintValidatorContext constraintValidatorContext) {
    if (StringUtils.isBlank(dateStr)) {
      return true;
    }
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
      LocalDate date = LocalDate.parse(dateStr, formatter);
      if (date.isAfter(LocalDate.now())) {
        return false;
      }
    } catch (DateTimeParseException e) {
      return false;
    }
    return true;
  }
}
