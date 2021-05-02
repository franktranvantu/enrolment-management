package com.franktran.enrolmentmanagement.enrolment;

import com.franktran.enrolmentmanagement.course.Course;
import com.franktran.enrolmentmanagement.course.CourseService;
import com.franktran.enrolmentmanagement.student.Student;
import com.franktran.enrolmentmanagement.student.StudentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class EnrolmentService {

  private final EnrolmentRepository enrolmentRepository;
  private final StudentService studentService;
  private final CourseService courseService;

  public EnrolmentService(EnrolmentRepository enrolmentRepository,
                          StudentService studentService,
                          CourseService courseService) {
    this.enrolmentRepository = enrolmentRepository;
    this.studentService = studentService;
    this.courseService = courseService;
  }

  public List<Enrolment> getAllEnrolments() {
    return enrolmentRepository.findAll();
  }

  public Enrolment getEnrolmentById(long id) {
    return enrolmentRepository.findById(id).orElse(null);
  }

  public Enrolment createEnrolment(Enrolment enrolment) {
    Optional<Enrolment> enrolmentByCourseIdAndStudentIdAndSemester = enrolmentRepository
        .findEnrolmentByCourseIdAndStudentIdAndSemester(enrolment.getCourse().getId(), enrolment.getStudent().getId(), enrolment.getSemester());
    if (enrolmentByCourseIdAndStudentIdAndSemester.isPresent()) {
      throw new IllegalArgumentException(String.format("Student %s taken course %s for semester %s",
          enrolment.getCourse().getName(), enrolment.getStudent().getName(), enrolment.getSemester()));
    }
    return enrolmentRepository.save(enrolment);
  }

  public Enrolment updateEnrolment(long id, Enrolment enrolment) {
    Enrolment existEnrolment = enrolmentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Enrolment with id %s does not exist", id)));
    Optional<Enrolment> enrolmentByCourseIdAndStudentIdAndSemester = enrolmentRepository
        .findEnrolmentByCourseIdAndStudentIdAndSemester(enrolment.getCourse().getId(), enrolment.getStudent().getId(), enrolment.getSemester());
    // Update course
    if (Objects.nonNull(enrolment.getCourse().getId())
        && !Objects.equals(existEnrolment.getCourse().getId(), enrolment.getCourse().getId())) {
      if (enrolmentByCourseIdAndStudentIdAndSemester.isPresent()) {
        throw new IllegalArgumentException(String.format("Student %s taken course %s for semester %s",
            enrolment.getCourse().getName(), enrolment.getStudent().getName(), enrolment.getSemester()));
      }
      Course course = courseService.getCourseById(enrolment.getCourse().getId());
      existEnrolment.setCourse(course);
    }
    // Update student
    if (Objects.nonNull(enrolment.getStudent().getId())
        && !Objects.equals(existEnrolment.getStudent().getId(), enrolment.getStudent().getId())) {
      if (enrolmentByCourseIdAndStudentIdAndSemester.isPresent()) {
        throw new IllegalArgumentException(String.format("Student %s taken course %s for semester %s",
            enrolment.getCourse().getName(), enrolment.getStudent().getName(), enrolment.getSemester()));
      }
      Student student = studentService.getStudentById(enrolment.getStudent().getId());
      existEnrolment.setStudent(student);
    }
    // Update semester
    if (Objects.nonNull(enrolment.getSemester())
        && !Objects.equals(existEnrolment.getSemester(), enrolment.getSemester())) {
      if (enrolmentByCourseIdAndStudentIdAndSemester.isPresent()) {
        throw new IllegalArgumentException(String.format("Student %s taken course %s for semester %s",
            enrolment.getCourse().getName(), enrolment.getStudent().getName(), enrolment.getSemester()));
      }
      existEnrolment.setSemester(enrolment.getSemester());
    }
    return enrolmentRepository.save(existEnrolment);
  }

  public void deleteEnrolmentById(long id) {
    boolean existsById = enrolmentRepository.existsById(id);
    if (!existsById) {
      throw new IllegalArgumentException(String.format("Enrolment with id %s not exists", id));
    }
    try {
      enrolmentRepository.deleteById(id);
    } catch (DataIntegrityViolationException e) {
      throw new IllegalStateException(String.format("Enrolment with id %d is being used by others!", id));
    }
  }
}
