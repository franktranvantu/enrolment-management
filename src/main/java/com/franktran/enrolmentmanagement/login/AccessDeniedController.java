package com.franktran.enrolmentmanagement.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AccessDeniedController {

    @GetMapping("/access-denied")
    public String showAccessDenied() {
        return "access-denied";
    }
}
