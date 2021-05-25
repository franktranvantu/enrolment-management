package com.franktran.enrolmentmanagement.enrolment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.franktran.enrolmentmanagement.course.Course;
import com.franktran.enrolmentmanagement.student.Student;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Enrolment {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinTable(
      name = "enrolment_student",
      joinColumns = {@JoinColumn(name = "enrolment_id")},
      inverseJoinColumns = {@JoinColumn(name = "student_id")}
  )
  @NotNull(message = "Student is mandatory")
  @JsonIgnore
  private Student student;

  @ManyToOne
  @JoinTable(
      name = "enrolment_course",
      joinColumns = {@JoinColumn(name = "enrolment_id")},
      inverseJoinColumns = {@JoinColumn(name = "course_id")}
  )
  @NotNull(message = "Course is mandatory")
  @JsonIgnore
  private Course course;

  @NotBlank(message = "Semester is mandatory")
  private String semester;

  public Enrolment(Course course, Student student, String semester) {
    this.student = student;
    this.course = course;
    this.semester = semester;
  }
}
