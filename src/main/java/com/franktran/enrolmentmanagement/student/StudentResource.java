package com.franktran.enrolmentmanagement.student;

import org.springframework.http.ResponseEntity;
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
  public ResponseEntity<List<Student>> all() {
    return ResponseEntity.ok(studentService.getAllStudents());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> one(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.getStudentById(id));
  }

  @PostMapping
  public ResponseEntity<Student> newStudent(@RequestBody Student newStudent) {
    return ResponseEntity.ok(studentService.createStudent(newStudent));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Student> replaceStudent(@PathVariable Long id, @RequestBody Student newStudent) {
    return ResponseEntity.ok(studentService.updateStudent(id, newStudent));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok().build();
  }

}
