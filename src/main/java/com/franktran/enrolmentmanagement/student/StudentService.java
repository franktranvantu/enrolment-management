
package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.exception.EmailAlreadyExistException;
import com.franktran.enrolmentmanagement.exception.StudentNotFoundException;
import com.franktran.enrolmentmanagement.exception.StudentReferByOtherException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.franktran.enrolmentmanagement.student.StudentMapper.MAPPER_INSTANCE;
import static com.franktran.enrolmentmanagement.student.StudentSpecification.*;

@Service
@RequiredArgsConstructor
public class StudentService {

  private final StudentRepository studentRepository;

  public List<Student> getAllStudents() {
    return studentRepository.findAll();
  }

  public Page<Student> getStudents(StudentCriteria criteria, Pageable pageRequest) {
    Specification<Student> conditions = Specification
        .where(hasDateRangeBetween(criteria.getDobRange()))
        .and(hasNameLike(criteria.getName()))
        .and(hasEmailLike(criteria.getEmail()));
    return studentRepository.findAll(conditions, pageRequest);
  }

  public Student getStudentById(long id) {
    return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(id));
  }

  public Student createStudent(Student student) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
    if (studentOptional.isPresent()) {
      throw new EmailAlreadyExistException(student.getEmail());
    }
    return studentRepository.save(student);
  }

  public StudentDto createStudent(StudentDto student) {
    Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
    if (studentOptional.isPresent()) {
      throw new EmailAlreadyExistException(student.getEmail());
    }
    Student studentEntity = MAPPER_INSTANCE.dtoToEntity(student);
    studentRepository.save(studentEntity);
    return MAPPER_INSTANCE.entityToDto(studentEntity);
  }

  public Student updateStudent(long studentId, Student student) {
    Student existStudent = studentRepository.findById(studentId).orElseThrow(() -> new StudentNotFoundException(studentId));
    if (Objects.nonNull(student.getName()) && !Objects.equals(existStudent.getName(), student.getName())) {
      existStudent.setName(student.getName());
    }
    if (Objects.nonNull(student.getEmail()) && !Objects.equals(existStudent.getEmail(), student.getEmail())) {
      Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
      if (studentOptional.isPresent()) {
        throw new EmailAlreadyExistException(student.getEmail());
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
      throw new StudentNotFoundException(studentId);
    }
    try {
      studentRepository.deleteById(studentId);
    } catch (DataIntegrityViolationException e) {
      throw new StudentReferByOtherException(student.get().getName());
    }
  }

}
