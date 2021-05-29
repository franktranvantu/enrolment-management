package com.franktran.enrolmentmanagement.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {

  @NotNull
  @NotEmpty
  private String username;
  @NotNull
  @NotEmpty
  private String email;
  @NotNull
  @NotEmpty
  private String password;
  private String confirmPassword;

}
