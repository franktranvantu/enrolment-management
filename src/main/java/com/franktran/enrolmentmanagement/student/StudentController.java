package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.config.security.UserRole;
import com.franktran.enrolmentmanagement.dto.DateRange;
import com.franktran.enrolmentmanagement.dto.ResultDto;
import com.franktran.enrolmentmanagement.dto.ResultStatus;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static com.franktran.enrolmentmanagement.config.security.UserRole.ADMIN;
import static com.franktran.enrolmentmanagement.config.security.UserRole.STUDENT;

@Controller
@RequestMapping("/student")
@SessionAttributes("username")
public class StudentController {

  private final StudentService studentService;
  private final StudentExcelExporter studentExcelExporter;
  private final StudentPdfExporter studentPdfExporter;

  public StudentController(StudentService studentService, StudentExcelExporter studentExcelExporter, StudentPdfExporter studentPdfExporter) {
    this.studentService = studentService;
    this.studentExcelExporter = studentExcelExporter;
    this.studentPdfExporter = studentPdfExporter;
  }

  @ModelAttribute("username")
  public String username(Authentication authentication) {
    return authentication.getName();
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ENROLMENT', 'ROLE_COURSE', 'ROLE_STUDENT')")
  public String index(@RequestParam(required = false) String name,
                      @RequestParam(required = false) String email,
                      @RequestParam(required = false) DateRange dobRange,
                      Model model,
                      Authentication authentication) {
    UserRole[] editableRoles = new UserRole[] {ADMIN, STUDENT};
    List<Student> students = studentService.getAllStudents(name, email, dobRange);
    model.addAttribute("username", authentication.getName());
    model.addAttribute("students", students);
    model.addAttribute("student", new Student(name, email, null));
    model.addAttribute("dobRange", dobRange);
    model.addAttribute("isEditable", UserRole.isEditable(editableRoles, authentication.getAuthorities()));
    return "student-list";
  }

  @PostMapping("/export-excel")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String exportExcel(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) DateRange dobRange,
                            HttpServletResponse response) throws IOException {
    List<Student> students = studentService.getAllStudents(name, email, dobRange);
    studentExcelExporter.export(response, students, "Students.xlsx");
    return "forward:/student";
  }

  @PostMapping("/export-pdf")
  @PreAuthorize("hasAnyAuthority('ADMIN:WRITE', 'STUDENT:WRITE')")
  public String exportPdf(@RequestParam(required = false) String name,
                            @RequestParam(required = false) String email,
                            @RequestParam(required = false) DateRange dobRange,
                            HttpServletResponse response) throws IOException {
    List<Student> students = studentService.getAllStudents(name, email, dobRange);
    studentPdfExporter.export(response, students, "Students.pdf");
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
    ResultDto result = new ResultDto();
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
      return "redirect:/student";
    } catch (Exception e) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage(e.getMessage());
      model.addAttribute("result", result);
      return "save-student";
    }
  }

  @PostMapping("/update-student")
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
    ResultDto result = new ResultDto();
    try {
      studentService.deleteStudent(id);
      result.setStatus(ResultStatus.SUCCESS);
      result.setMessage("Deleted student successful!");
    } catch (Exception e) {
      result.setStatus(ResultStatus.FAIL);
      result.setMessage(e.getMessage());
    }
    ra.addFlashAttribute("result", result);
    return "redirect:/student";
  }

}
