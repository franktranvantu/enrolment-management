package com.franktran.enrolmentmanagement.profile;

import com.franktran.enrolmentmanagement.config.security.auth.UserService;
import com.franktran.enrolmentmanagement.dto.Result;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@SessionAttributes("username")
public class ChangePasswordController {

  private final UserService userService;

  public ChangePasswordController(UserService userService) {
    this.userService = userService;
  }

  @ModelAttribute("username")
  public String username(Authentication authentication) {
    return authentication.getName();
  }

  @GetMapping("/change-password")
  public String showChangePassword() {
    return "change-password";
  }

  @PostMapping("/change-password")
  public String processChangePassword(@RequestParam String username,
                                      @RequestParam String password,
                                      @RequestParam String newPassword,
                                      RedirectAttributes ra) {
    Result result = new Result();
    try {
      userService.changePassword(username, password, newPassword);
      result.setStatus(ResultStatus.SUCCESS);
      result.setMessage("Change password successful!");
    } catch (Exception e) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage(e.getMessage());
    }
    ra.addFlashAttribute("result", result);
    return "redirect:/change-password";
  }
}
