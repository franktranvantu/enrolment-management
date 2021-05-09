package com.franktran.enrolmentmanagement.config.security.auth;

import com.franktran.enrolmentmanagement.config.security.UserRole;
import com.franktran.enrolmentmanagement.dto.UserDto;
import com.franktran.enrolmentmanagement.exception.UserAlreadyExistException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with username %s does not exists"));
        }
        return new CustomUserDetails(user);
    }

    public User changePassword(String username, String password, String newPassword) {
        User user = userRepository.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException(String.format("User with username %s does not exists"));
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Current password incorrect");
        }
        String encodePassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodePassword);
        return userRepository.save(user);
    }

    public void updateResetPasswordToken(String token, String email) {
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            user.setRequestToken(token);
            userRepository.save(user);
        } else {
            throw new UsernameNotFoundException(String.format("User with email %s does not exists", email));
        }
    }

    public User getByRequestToken(String token) {
        return userRepository.findByRequestToken(token);
    }

    public void updatePassword(User user, String newPassword) {
        String password = passwordEncoder.encode(newPassword);
        user.setPassword(password);
        user.setRequestToken(null);
        userRepository.save(user);
    }


  public User registerUser(String token, UserDto userDto) {
      User userByEmail = userRepository.findByEmail(userDto.getEmail());
      if (Objects.nonNull(userByEmail)) {
          throw new UserAlreadyExistException("Email already exist!");
      }
      User user = new User();
      user.setUsername(userDto.getUsername());
      user.setPassword(passwordEncoder.encode(userDto.getPassword()));
      user.setEmail(userDto.getEmail());
      user.setRole(UserRole.ENROLMENT.name());
      user.setRequestToken(token);

      return userRepository.save(user);
  }

    public void activeUser(User user) {
        user.setEnabled(true);
        user.setRequestToken(null);
        userRepository.save(user);
    }
}
