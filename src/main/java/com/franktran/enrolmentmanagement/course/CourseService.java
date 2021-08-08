package com.franktran.enrolmentmanagement.course;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

  private final CourseRepository courseRepository;

  public CourseService(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public List<Course> getAllCourses() {
    return courseRepository.findAll();
  }

  public Page<Course> getCourses(CourseCriteria courseCriteria, Pageable pageRequest) {
    Specification<Course> conditions = Specification.where(CourseSpecification.hasNameLike(courseCriteria.getName()));
    return courseRepository.findAll(conditions, pageRequest);
  }

  public Course getCourseById(long id) {
    return courseRepository.findById(id).orElse(null);
  }

  public Course createCourse(Course course) {
    return courseRepository.save(course);
  }

  public Course updateCourse(long id, Course course) {
    Course existCourse = courseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(String.format("Course with id does not exist")));
    if (Objects.nonNull(course.getName()) && !Objects.equals(existCourse.getName(), course.getName())) {
      Optional<Course> courseByName = courseRepository.findCourseByName(course.getName());
      if (courseByName.isPresent()) {
        throw new IllegalArgumentException("Course already exists");
      }
      existCourse.setName(course.getName());
    }
    return courseRepository.save(existCourse);
  }

  public void deleteCourse(long id) {
      courseRepository.findById(id).ifPresentOrElse(course -> {
        try {
            courseRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
          throw new IllegalStateException(String.format("Course %s is being used by others!", course.getName()));
        }
      }, () -> {
        throw new IllegalStateException(String.format("Course with id %s not exists", id));
      });
  }
}
