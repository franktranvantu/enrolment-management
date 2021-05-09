package com.franktran.enrolmentmanagement.config.security.auth;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserBootStrap implements CommandLineRunner {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserBootStrap(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void run(String... args) throws Exception {
    User admin = new User("admin", passwordEncoder.encode("admin123"), "admindemo@yopmail.com", "ADMIN", true);
    User enrolment = new User("enrolment", passwordEncoder.encode("enrolment123"), "enrolmentdemo@yopmail.com", "ENROLMENT", true);
    User course = new User("course", passwordEncoder.encode("course123"), "coursedemo@yopmail.com", "COURSE", true);
    User student = new User("student", passwordEncoder.encode("student123"), "studentdemo@yopmail.com", "STUDENT", true);
    userRepository.saveAll(Arrays.asList(admin, enrolment, course, student));
  }
}
