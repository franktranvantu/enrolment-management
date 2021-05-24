package com.franktran.enrolmentmanagement.exception;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
    super("Could not find student with id" + id);
  }
}
