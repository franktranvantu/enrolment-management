package com.franktran.enrolmentmanagement.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {

  private final StudentService studentService;

  public StudentRestController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping
  public ResponseEntity<Page<Student>> getStudents(StudentCriteria studentCriteria, @PageableDefault Pageable pageRequest) {
    return ResponseEntity.ok(studentService.getStudents(studentCriteria, pageRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    return ResponseEntity.ok(studentService.getStudentById(id));
  }

  @PostMapping
  public ResponseEntity<StudentDto> createStudent(@RequestBody @Valid StudentDto student) {
    return ResponseEntity.ok(studentService.createStudent(student));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
    return ResponseEntity.ok(studentService.updateStudent(id, student));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok().build();
  }
}
