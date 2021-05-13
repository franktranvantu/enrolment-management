package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.dto.DateRange;
import com.franktran.enrolmentmanagement.dto.SearchCriteria;
import com.franktran.enrolmentmanagement.exception.DataInValidException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public List<Student> getAllStudents(String name, String email, DateRange dobRange) {
    StudentSpecification nameSpec = new StudentSpecification(new SearchCriteria("name", name));
    StudentSpecification emailSpec = new StudentSpecification(new SearchCriteria("email", email));
    StudentSpecification dobSpec = new StudentSpecification(new SearchCriteria("dob", dobRange));
    return studentRepository.findAll(Specification.where(nameSpec).and(emailSpec).and(dobSpec));
  }

  public Student getStudentById(long id) {
    return studentRepository.findById(id).orElse(null);
  }

  public Student createStudent(Student student, String viewName) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
    if (studentOptional.isPresent()) {
      throw new DataInValidException("Email already exists", viewName);
    }
    return studentRepository.save(student);
  }

  public Student updateStudent(long studentId, Student student, String viewName) {
    Student existStudent = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException(String.format("Student with id %s not exists", studentId)));
    if (Objects.nonNull(student.getName()) && !Objects.equals(existStudent.getName(), student.getName())) {
      existStudent.setName(student.getName());
    }
    if (Objects.nonNull(student.getEmail()) && !Objects.equals(existStudent.getEmail(), student.getEmail())) {
      Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
      if (studentOptional.isPresent()) {
        throw new IllegalArgumentException("Email already exists");
      }
      existStudent.setEmail(student.getEmail());
    }
    if (Objects.nonNull(student.getDob()) && !Objects.equals(existStudent.getDob(), student.getDob())) {
      existStudent.setDob(student.getDob());
    }

    return studentRepository.save(existStudent);
  }

  public void deleteStudent(long studentId) {
    Optional<Student> student = studentRepository.findById(studentId);
    if (!student.isPresent()) {
      throw new IllegalArgumentException(String.format("Student with id %s not exists", studentId));
    }
    try {
      studentRepository.deleteById(studentId);
    } catch (DataIntegrityViolationException e) {
      throw new IllegalStateException(String.format("Student %s is being used by others!", student.get().getName()));
    }
  }

}
