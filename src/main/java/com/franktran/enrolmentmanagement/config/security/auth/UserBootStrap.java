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
    User admin = new User("admin", passwordEncoder.encode("admin123"), "ADMIN");
    User enrolment = new User("enrolment", passwordEncoder.encode("enrolment123"), "ENROLMENT");
    User course = new User("course", passwordEncoder.encode("course123"), "COURSE");
    User student = new User("student", passwordEncoder.encode("student123"), "STUDENT");
    userRepository.saveAll(Arrays.asList(admin, enrolment, course, student));
  }
}
