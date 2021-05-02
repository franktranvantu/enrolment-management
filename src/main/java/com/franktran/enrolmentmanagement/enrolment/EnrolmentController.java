package com.franktran.enrolmentmanagement.enrolment;

import com.franktran.enrolmentmanagement.config.security.UserRole;
import com.franktran.enrolmentmanagement.course.CourseService;
import com.franktran.enrolmentmanagement.dto.ResultDto;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import com.franktran.enrolmentmanagement.student.StudentService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.franktran.enrolmentmanagement.config.security.UserRole.ADMIN;
import static com.franktran.enrolmentmanagement.config.security.UserRole.ENROLMENT;

@Controller
@SessionAttributes("username")
public class EnrolmentController {

    private final EnrolmentService enrolmentService;
    private final CourseService courseService;
    private final StudentService studentService;

    public EnrolmentController(EnrolmentService enrolmentService,
                               CourseService courseService,
                               StudentService studentService) {
        this.enrolmentService = enrolmentService;
        this.courseService = courseService;
        this.studentService = studentService;
    }

    @ModelAttribute("username")
    public String username(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ENROLMENT', 'ROLE_COURSE', 'ROLE_STUDENT')")
    public String index(Model model, Authentication authentication) {
        UserRole[] editableRoles = new UserRole[] {ADMIN, ENROLMENT};
        List<Enrolment> enrolments = enrolmentService.getAllEnrolments();
        model.addAttribute("enrolments", enrolments);
        model.addAttribute("isEditable", UserRole.isEditable(editableRoles, authentication.getAuthorities()));
        return "enrolment-list";
    }

    @GetMapping("/create-enrolment")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'ENROLMENT:WRITE')")
    public String showCreateEnrolment(@ModelAttribute("enrolment") Enrolment enrolment, Model model) {
        model.addAttribute("action", "Create");
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("students", studentService.getAllStudents());
        return "save-enrolment";
    }

    @PostMapping("/save-enrolment")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'ENROLMENT:WRITE')")
    public String saveEnrolment(@ModelAttribute("enrolment") @Valid Enrolment enrolment,
                                BindingResult bindingResult,
                                RedirectAttributes ra,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", Objects.isNull(enrolment.getId()) ? "Create" : "Update");
            return "save-enrolment";
        }
        ResultDto result = new ResultDto();
        try {
            if (Objects.isNull(enrolment.getId())) {
                model.addAttribute("action", "Create");
                enrolmentService.createEnrolment(enrolment);
                result.setMessage("Created enrolment successful!");
            } else {
                model.addAttribute("action", "Update");
                enrolmentService.updateEnrolment(enrolment.getId(), enrolment);
                result.setMessage("Updated enrolment successful!");
            }
            result.setStatus(ResultStatus.SUCCESS);
            ra.addFlashAttribute("result", result);
            model.addAttribute("enrolments", enrolmentService.getAllEnrolments());
            return "redirect:/";
        } catch (Exception e) {
            result.setStatus(ResultStatus.FAIL);
            result.setMessage(e.getMessage());
            model.addAttribute("result", result);
            model.addAttribute("courses", courseService.getAllCourses());
            model.addAttribute("students", studentService.getAllStudents());
            if (Objects.isNull(enrolment.getId())) {
                return "save-enrolment";
            }
            model.addAttribute("enrolment", enrolment);
            return "save-enrolment";
        }
    }

    @PostMapping("/update-enrolment")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'ENROLMENT:WRITE')")
    public String showUpdateEnrolment(@RequestParam long id, Model model) {
        Enrolment enrolment = enrolmentService.getEnrolmentById(id);
        model.addAttribute("action", "Update");
        model.addAttribute("enrolment", enrolment);
        model.addAttribute("courses", courseService.getAllCourses());
        model.addAttribute("students", studentService.getAllStudents());
        return "save-enrolment";
    }

    @PostMapping("/delete-enrolment")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'ENROLMENT:WRITE')")
    public String deleteEnrolment(@RequestParam long id, RedirectAttributes ra) {
        ResultDto result = new ResultDto();
        try {
            enrolmentService.deleteEnrolmentById(id);
            result.setStatus(ResultStatus.SUCCESS);
            result.setMessage("Deleted enrolment successful!");
            ra.addFlashAttribute("result", result);
        } catch (Exception e) {
            result.setStatus(ResultStatus.FAIL);
            result.setMessage(e.getMessage());
        }
        ra.addFlashAttribute("result", result);
        return "redirect:/";
    }
}
