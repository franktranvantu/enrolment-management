package com.franktran.enrolmentmanagement.student;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/admin/students")
@RequiredArgsConstructor
public class StudentRestController {

  private final StudentService studentService;

  @GetMapping
  public ResponseEntity<Page<Student>> getStudents(StudentCriteria studentCriteria, @PageableDefault Pageable pageRequest) {
    return ResponseEntity.ok(studentService.getStudents(studentCriteria, pageRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
    return ResponseEntity.ok().body(studentService.getStudentById(id));
  }

  @PostMapping
  public ResponseEntity<StudentDto> createStudent(@RequestBody @Valid StudentDto student) {
    URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/students").toUriString());
    return ResponseEntity.created(uri).body(studentService.createStudent(student));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
    return ResponseEntity.ok().body(studentService.updateStudent(id, student));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteStudent(@PathVariable Long id) {
    studentService.deleteStudent(id);
    return ResponseEntity.ok().build();
  }
}
