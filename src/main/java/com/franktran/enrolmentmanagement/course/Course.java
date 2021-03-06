package com.franktran.enrolmentmanagement.course;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.franktran.enrolmentmanagement.enrolment.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank(message = "Name is mandatory")
  private String name;
  @OneToMany(mappedBy = "course")
  @JsonIgnore
  private Set<Enrolment> enrolments = new HashSet<>();

  public Course(String name) {
    this.name = name;
  }

}
