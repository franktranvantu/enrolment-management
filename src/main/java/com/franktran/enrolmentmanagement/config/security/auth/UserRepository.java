package com.franktran.enrolmentmanagement.config.security.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsername(String username);

  User findByEmail(String email);

  User findByResetPasswordToken(String token);

}
