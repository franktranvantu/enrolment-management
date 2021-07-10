package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.dto.DateRange;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class StudentCriteria {

  private String name;
  private String email;
  private DateRange dobRange;
}
