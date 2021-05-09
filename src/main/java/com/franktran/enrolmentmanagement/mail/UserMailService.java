package com.franktran.enrolmentmanagement.mail;

import com.franktran.enrolmentmanagement.config.security.auth.UserRepository;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

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

  public void sendResetEmail(String recipientEmail, String link)
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

  public void sendRegisterEmail(String recipientEmail, String link)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom(mailProperties.getUsername(), "Enrolment Support Team");
    helper.setTo(recipientEmail);

    String subject = "Here's the link to activate your account";

    String content = "<p>Hello,</p>"
        + "<p>You have requested to create new account.</p>"
        + "<p>Click the link below to activate your new account:</p>"
        + "<p><a href=\"" + link + "\">Active account</a></p>";

    helper.setSubject(subject);

    helper.setText(content, true);

    mailSender.send(message);
  }
}
