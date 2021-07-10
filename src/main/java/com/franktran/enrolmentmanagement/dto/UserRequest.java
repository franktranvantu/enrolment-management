package com.franktran.enrolmentmanagement.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

  @NotBlank(message = "Username is required")
  private String username;
  @NotBlank(message = "User is required")
  private String email;
  @NotBlank(message = "Password is required")
  private String password;
  private String confirmPassword;

}
