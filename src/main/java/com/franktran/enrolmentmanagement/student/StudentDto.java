package com.franktran.enrolmentmanagement.student;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.franktran.enrolmentmanagement.util.Utility;
import com.franktran.enrolmentmanagement.validation.ValidBirthDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {

  private Long id;

  @NotBlank(message = "Name is required")
  @Length(min = 3, message = "Name should be minimum of 3 characters")
  private String name;

  @NotBlank(message = "Email is required")
  @Email(message = "Email is invalid")
  private String email;

  @ValidBirthDate(message = "Birthdate should be a past and formatted " + Utility.DATE_FORMAT)
  private String dob;
}
