package com.franktran.enrolmentmanagement.login;

import com.franktran.enrolmentmanagement.config.security.auth.User;
import com.franktran.enrolmentmanagement.config.security.auth.UserService;
import com.franktran.enrolmentmanagement.dto.ResultDto;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import com.franktran.enrolmentmanagement.mail.UserMailService;
import com.franktran.enrolmentmanagement.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class ForgotPasswordController {

  private final UserService userService;
  private final UserMailService userMailService;

  public ForgotPasswordController(UserService userService, UserMailService userMailService) {
    this.userService = userService;
    this.userMailService = userMailService;
  }

  @GetMapping("/forgot-password")
  public String showForgotPassword() {
    return "forgot-password";
  }

  @PostMapping("/forgot-password")
  public String processForgotPassword(@RequestParam String email,
                                      HttpServletRequest request,
                                      RedirectAttributes ra) {
    ResultDto result = new ResultDto();
    String token = RandomString.make(30);
    try {
      userService.updateResetPasswordToken(token, email);
      String resetPasswordLink = Utility.getSiteURL(request) + "/reset-password?token=" + token;
      userMailService.sendResetEmail(email, resetPasswordLink);
      result.setStatus(ResultStatus.SUCCESS);
      result.setMessage("We have sent a reset password link to your email!");
    } catch (Exception e) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage(e.getMessage());
    }
    ra.addFlashAttribute("result", result);
    return "redirect:/forgot-password";
  }

  @GetMapping("/reset-password")
  public String showResetPassword(@RequestParam String token, Model model) {
    User user = userService.getByRequestToken(token);
    if (Objects.isNull(user)) {
      ResultDto result = new ResultDto();
      result.setStatus(ResultStatus.FAIL);
      result.setMessage("Invalid Token");
      model.addAttribute("result", result);
      return "reset-password";
    }
    model.addAttribute("token", token);
    return "reset-password";
  }

  @PostMapping("/reset-password")
  public String processResetPassword(@RequestParam String token,
                                     @RequestParam String password,
                                     Model model) {
    User user = userService.getByRequestToken(token);
    ResultDto result = new ResultDto();
    if (Objects.isNull(user)) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage("Invalid Token");
    } else {
      userService.updatePassword(user, password);
      result.setStatus(ResultStatus.SUCCESS);
      result.setMessage("You have successfully changed your password.");
    }
    model.addAttribute("result", result);
    return "reset-password";
  }
}
