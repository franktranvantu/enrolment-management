package com.franktran.enrolmentmanagement.student;

import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Order(2)
public class StudentBootStrap implements CommandLineRunner {

  private final StudentRepository studentRepository;
  private final Environment environment;

  public StudentBootStrap(StudentRepository studentRepository, Environment environment) {
    this.studentRepository = studentRepository;
    this.environment = environment;
  }

  @Override
  public void run(String... args) {
    Faker faker = new Faker();
    final int LIMIT = environment.getProperty("student.limit", Integer.class);
    List<Student> students = Stream.iterate(1, i -> i + 1)
        .map(i -> new Student(
          String.format("Student %d", i),
          String.format("student%d@gmail.com", i),
          faker.date().birthday(10, 50).toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        ))
        .limit(LIMIT)
        .collect(Collectors.toList());

    studentRepository.saveAll(students);
  }
}
