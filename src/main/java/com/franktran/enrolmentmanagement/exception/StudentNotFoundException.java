package com.franktran.enrolmentmanagement.exception;

public class StudentNotFoundException extends RuntimeException {

  public StudentNotFoundException(Long id) {
    super(String.format("Student with id %s not found!", id));
  }
}
