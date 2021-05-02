package com.franktran.enrolmentmanagement.course;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Order(1)
public class CourseBootStrap implements CommandLineRunner {

  private final CourseRepository courseRepository;

  public CourseBootStrap(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    Faker faker = new Faker();
    List<Course> courses = Stream.iterate(1, i -> i + 1)
        .map(i -> new Course(faker.book().title()))
        .limit(50)
        .collect(Collectors.toList());

    courseRepository.saveAll(courses);
  }
}
