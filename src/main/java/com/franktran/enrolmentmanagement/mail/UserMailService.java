package com.franktran.enrolmentmanagement.mail;

import com.franktran.enrolmentmanagement.config.security.auth.User;
import com.franktran.enrolmentmanagement.config.security.auth.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Service
public class UserMailService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JavaMailSender mailSender;
  private final MailProperties mailProperties;

  public UserMailService(UserRepository userRepository,
                         PasswordEncoder passwordEncoder,
                         JavaMailSender mailSender,
                         MailProperties mailProperties) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.mailSender = mailSender;
    this.mailProperties = mailProperties;
  }

  public void updateResetPasswordToken(String token, String email) {
    User user = userRepository.findByEmail(email);
    if (Objects.nonNull(user)) {
      user.setResetPasswordToken(token);
      userRepository.save(user);
    } else {
      throw new UsernameNotFoundException(String.format("User with email %s does not exists", email));
    }
  }

  public User getByResetPasswordToken(String token) {
    return userRepository.findByResetPasswordToken(token);
  }

  public void updatePassword(User user, String newPassword) {
    String password = passwordEncoder.encode(newPassword);
    user.setPassword(password);
    user.setResetPasswordToken(null);
    userRepository.save(user);
  }

  public void sendEmail(String recipientEmail, String link)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(mailProperties.getUsername(), "Enrolment Support Team");
    helper.setTo(recipientEmail);

    String subject = "Here's the link to reset your password";

    String content = "<p>Hello,</p>"
        + "<p>You have requested to reset your password.</p>"
        + "<p>Click the link below to change your password:</p>"
        + "<p><a href=\"" + link + "\">Change my password</a></p>"
        + "<br>"
        + "<p>Ignore this email if you do remember your password, "
        + "or you have not made the request.</p>";

    helper.setSubject(subject);

    helper.setText(content, true);

    mailSender.send(message);
  }
}
