package com.franktran.enrolmentmanagement.login;

import com.franktran.enrolmentmanagement.config.security.auth.User;
import com.franktran.enrolmentmanagement.config.security.auth.UserService;
import com.franktran.enrolmentmanagement.dto.ResultDto;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import com.franktran.enrolmentmanagement.dto.UserDto;
import com.franktran.enrolmentmanagement.exception.UserAlreadyExistException;
import com.franktran.enrolmentmanagement.mail.UserMailService;
import com.franktran.enrolmentmanagement.util.Utility;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Controller
public class RegisterController {

    private final UserService userService;
    private final UserMailService userMailService;

    public RegisterController(UserService userService, UserMailService userMailService) {
        this.userService = userService;
        this.userMailService = userMailService;
    }

    @GetMapping("/register")
    public String showRegister(@ModelAttribute("user") UserDto user) {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") UserDto user,
                                  HttpServletRequest request,
                                  RedirectAttributes ra,
                                  Model model) {
        ResultDto result = new ResultDto();
        String token = RandomString.make(30);
        try {
            userService.registerUser(token, user);
            String registerLink = String.format("%s/register/active?token=%s", Utility.getSiteURL(request), token);
            userMailService.sendRegisterEmail(user.getEmail(), registerLink);
            result.setStatus(ResultStatus.SUCCESS);
            result.setMessage("We have sent an activate link to your email!");
            ra.addFlashAttribute("result", result);
            return "redirect:/login";
        } catch (UserAlreadyExistException | MessagingException | UnsupportedEncodingException e) {
            result.setStatus(ResultStatus.FAIL);
            result.setMessage(e.getMessage());
            model.addAttribute("result", result);
            return "register";
        }
    }

    @GetMapping("/register/active")
    public String activeRegister(String token, Model model) {
        User user = userService.getByRequestToken(token);
        if (Objects.isNull(user)) {
            ResultDto result = new ResultDto();
            result.setStatus(ResultStatus.FAIL);
            result.setMessage("Invalid Token");
            model.addAttribute("result", result);
        } else {
            userService.activeUser(user);
        }
        return "redirect:/login";

    }
}
