package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.config.security.Role;
import com.franktran.enrolmentmanagement.dto.Result;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
import io.sentry.Sentry;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

import static com.franktran.enrolmentmanagement.config.security.Role.ADMIN;
import static com.franktran.enrolmentmanagement.config.security.Role.STUDENT;

@Controller
@RequestMapping("/admin/student")
@SessionAttributes("username")
@RequiredArgsConstructor
public class StudentController {

  private final StudentService studentService;
  private final StudentExportService studentExportService;

  @ModelAttribute("username")
  public String username(Authentication authentication) {
    return authentication.getName();
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ENROLMENT', 'ROLE_COURSE', 'ROLE_STUDENT')")
  public String index(StudentCriteria studentCriteria,
                      @PageableDefault Pageable pageRequest,
                      Model model,
                      Authentication authentication) {
    Role[] editableRoles = new Role[] {ADMIN, STUDENT};
    Page<Student> page = studentService.getStudents(studentCriteria, pageRequest);
    model.addAttribute("username", authentication.getName());
    model.addAttribute("students", page.getContent());
    model.addAttribute("student", new Student(studentCriteria.getName(), studentCriteria.getEmail(), null));
    model.addAttribute("dobRange", studentCriteria.getDobRange());
    model.addAttribute("isEditable", Role.isEditable(editableRoles, authentication.getAuthorities()));
    return "student-list";
  }

  @GetMapping("/export-{type}")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String exportExcel(StudentCriteria studentCriteria,
                            @PageableDefault Pageable pageRequest,
                            @PathVariable String type,
                            HttpServletResponse response) throws IOException {
    Page<Student> page = studentService.getStudents(studentCriteria, pageRequest);
    studentExportService.export(response, page.getContent(), type);
    return "forward:/student";
  }

  @GetMapping("/create-student")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String showCreateStudent(@ModelAttribute("student") Student student, Model model) {
    model.addAttribute("action", "Create");
    return "save-student";
  }

  @PostMapping("/save-student")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String saveStudent(@ModelAttribute("student") @Valid Student student,
                            BindingResult bindingResult,
                            RedirectAttributes ra,
                            Model model) {
    if (bindingResult.hasErrors()) {
      model.addAttribute("action", Objects.isNull(student.getId()) ? "Create" : "Update");
      return "save-student";
    }
    Result result = new Result();
    try {
      if (Objects.isNull(student.getId())) {
        model.addAttribute("action", "Create");
        studentService.createStudent(student);
        result.setMessage("Created student successful!");
      } else {
        model.addAttribute("action", "Update");
        studentService.updateStudent(student.getId(), student);
        result.setMessage("Updated student successful!");
      }
      result.setStatus(ResultStatus.SUCCESS);
      ra.addFlashAttribute("result", result);
      return "redirect:/admin/student";
    } catch (Exception e) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage(e.getMessage());
      model.addAttribute("result", result);
      Sentry.captureException(e);
      return "save-student";
    }
  }

  @GetMapping("/update-student")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String showUpdateStudent(@RequestParam long id, Model model) {
    Student student = studentService.getStudentById(id);
    model.addAttribute("action", "Update");
    model.addAttribute("student", student);
    return "save-student";
  }

  @PostMapping("/delete-student")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String deleteStudent(@RequestParam long id, RedirectAttributes ra) {
    Result result = new Result();
    try {
      studentService.deleteStudent(id);
      result.setStatus(ResultStatus.SUCCESS);
      result.setMessage("Deleted student successful!");
    } catch (Exception e) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage(e.getMessage());
      Sentry.captureException(e);
    }
    ra.addFlashAttribute("result", result);
    return "redirect:/admin/student";
  }

}
