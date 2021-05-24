package com.franktran.enrolmentmanagement.student;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentResource {

  private final StudentService studentService;

  public StudentResource(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public List<Student> all() {
    return studentService.getAllStudents();
  }

  @GetMapping("/{id}")
  public Student one(@PathVariable Long id) {
    return studentService.getStudentById(id);
  }

  @PostMapping
  public Student newStudent(@RequestBody Student newStudent) {
    return studentService.createStudent(newStudent);
  }

  @PutMapping("/{id}")
  public Student replaceStudent(@PathVariable Long id, @RequestBody Student newStudent) {
    return studentService.updateStudent(id, newStudent);
  }

  @DeleteMapping("/{id}")
  public void deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
  }

}
