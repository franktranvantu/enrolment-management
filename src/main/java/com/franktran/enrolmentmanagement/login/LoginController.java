package com.franktran.enrolmentmanagement.login;

import com.franktran.enrolmentmanagement.dto.Result;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Objects;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String showLogin(Model model, HttpSession httpSession) {
        Result result = new Result();
        Object error = httpSession.getAttribute("error");
        if (Objects.nonNull(error)) {
            result.setStatus(ResultStatus.FAIL);
            result.setMessage(Objects.toString(error));
            model.addAttribute("result", result);
            httpSession.removeAttribute("error");
        }
        Object message = httpSession.getAttribute("message");
        if (Objects.nonNull(message)) {
            result.setStatus(ResultStatus.SUCCESS);
            result.setMessage(Objects.toString(message));
            model.addAttribute("result", result);
            httpSession.removeAttribute("message");
        }
        return "login";
    }
}
