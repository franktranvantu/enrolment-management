package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.util.Utility;
import com.franktran.enrolmentmanagement.validation.ValidBirthDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentDto {

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email is invalid")
  private String email;

  @ValidBirthDate(message = "Birthday should be a past date and format should be " + Utility.DATE_FORMAT)
  private String dob;
}
