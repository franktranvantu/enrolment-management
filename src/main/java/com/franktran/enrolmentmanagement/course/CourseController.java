package com.franktran.enrolmentmanagement.course;

import com.franktran.enrolmentmanagement.config.security.UserRole;
import com.franktran.enrolmentmanagement.dto.Result;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
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
import static com.franktran.enrolmentmanagement.config.security.UserRole.COURSE;

@Controller
@RequestMapping("/course")
@SessionAttributes("username")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @ModelAttribute("username")
    public String username(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ENROLMENT', 'ROLE_COURSE', 'ROLE_STUDENT')")
    public String index(Model model, Authentication authentication) {
        UserRole[] editableRoles = new UserRole[] {ADMIN, COURSE};
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        model.addAttribute("isEditable", UserRole.isEditable(editableRoles, authentication.getAuthorities()));
        return "course-list";
    }

    @GetMapping("/create-course")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'COURSE:WRITE')")
    public String showCreateCourse(@ModelAttribute("course") Course course, Model model) {
        model.addAttribute("action", "Create");
        return "save-course";
    }

    @PostMapping("/save-course")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'COURSE:WRITE')")
    public String saveCourse(@ModelAttribute("course") @Valid Course course,
                             BindingResult bindingResult,
                             RedirectAttributes ra,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("action", Objects.isNull(course.getId()) ? "Create" : "Update");
            return "save-course";
        }
        Result result = new Result();
        try {
            if (Objects.isNull(course.getId())) {
                model.addAttribute("action", "Create");
                courseService.createCourse(course);
                result.setMessage("Created course successful!");
            } else {
                model.addAttribute("action", "Update");
                courseService.updateCourse(course.getId(), course);
                result.setMessage("Updated course successful!");
            }
            result.setStatus(ResultStatus.SUCCESS);
            ra.addFlashAttribute("result", result);
            return "redirect:/course";
        } catch (Exception e) {
            result.setStatus(ResultStatus.FAIL);
            result.setMessage(e.getMessage());
            model.addAttribute("result", result);
            return "save-course";
        }
    }

    @PostMapping("/update-course")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'COURSE:WRITE')")
    public String showUpdateCourse(@RequestParam long id, Model model) {
        Course course = courseService.getCourseById(id);
        model.addAttribute("action", "Update");
        model.addAttribute("course", course);
        return "save-course";
    }

    @PostMapping("/delete-course")
    @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'COURSE:WRITE')")
    public String deleteCourse(@RequestParam long id, RedirectAttributes ra) {
        Result result = new Result();
        try {
            courseService.deleteCourse(id);
            result.setStatus(ResultStatus.SUCCESS);
            result.setMessage("Deleted course successful!");
        } catch (Exception e) {
            result.setStatus(ResultStatus.FAIL);
            result.setMessage(e.getMessage());
        }
        ra.addFlashAttribute("result", result);
        return "redirect:/student";
    }
}
