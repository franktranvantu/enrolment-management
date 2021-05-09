package com.franktran.enrolmentmanagement.config.security.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String username;
  private String password;
  private String email;
  private String role;
  private String requestToken;
  private boolean isEnabled;

  public User(String username, String password, String email, String role, boolean isEnabled) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.role = role;
    this.isEnabled = isEnabled;
  }
}
