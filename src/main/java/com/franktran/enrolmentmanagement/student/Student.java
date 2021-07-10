package com.franktran.enrolmentmanagement.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.franktran.enrolmentmanagement.enrolment.Enrolment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  private LocalDate dob;
  
  @OneToMany(mappedBy = "student")
  @JsonIgnore
  private Set<Enrolment> enrolments = new HashSet<>();

  public Student(String name, String email, LocalDate dob) {
    this.name = name;
    this.email = email;
    this.dob = dob;
  }
}
