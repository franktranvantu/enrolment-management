package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.exception.EmailAlreadyExistException;
import com.franktran.enrolmentmanagement.exception.StudentNotFoundException;
import com.franktran.enrolmentmanagement.exception.StudentReferByOtherException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import io.sentry.Sentry;

@ControllerAdvice
public class StudentAdvice {

  @ResponseBody
  @ExceptionHandler(StudentNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String studentNotFoundHandler(StudentNotFoundException ex) {
//    Sentry.captureException(ex);
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(EmailAlreadyExistException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String emailAlreadyExistHandler(EmailAlreadyExistException ex) {
    Sentry.captureException(ex);
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(StudentReferByOtherException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String studentReferByOtherHandler(StudentReferByOtherException ex) {
    Sentry.captureException(ex);
    return ex.getMessage();
  }
}
