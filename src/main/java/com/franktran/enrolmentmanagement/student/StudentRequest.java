package com.franktran.enrolmentmanagement.student;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class StudentRequest {

  @NotBlank(message = "Name is required")
  private String name;
  @NotBlank(message = "Email is required")
  @Email
  private String email;
}
