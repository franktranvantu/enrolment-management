package com.franktran.enrolmentmanagement.enrolment;

import com.franktran.enrolmentmanagement.course.CourseService;
import com.franktran.enrolmentmanagement.student.StudentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Order(3)
public class EnrolmentBootStrap implements CommandLineRunner {

  private final EnrolmentRepository enrolmentRepository;
  private final CourseService courseService;
  private final StudentService studentService;

  public EnrolmentBootStrap(EnrolmentRepository enrolmentRepository, CourseService courseService, StudentService studentService) {
    this.enrolmentRepository = enrolmentRepository;
    this.courseService = courseService;
    this.studentService = studentService;
  }

  @Override
  public void run(String... args) throws Exception {
    List<Enrolment> enrolments = Stream.iterate(1, i -> i + 1)
        .map(i -> new Enrolment(courseService.getCourseById(i), studentService.getStudentById(i), "2021A"))
        .limit(50)
        .collect(Collectors.toList());

    enrolmentRepository.saveAll(enrolments);
  }
}
