package com.franktran.enrolmentmanagement.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseRestController {

  private final CourseService courseService;

  public CourseRestController(CourseService courseService) {
    this.courseService = courseService;
  }

  @GetMapping
  public ResponseEntity<Page<Course>> getCourses(CourseCriteria courseCriteria, @PageableDefault Pageable pageRequest) {
    return ResponseEntity.ok(courseService.getCourses(courseCriteria, pageRequest));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
    return ResponseEntity.ok(courseService.getCourseById(id));
  }

  @PostMapping
  public ResponseEntity<Course> createCourse(@RequestBody Course course) {
    return ResponseEntity.ok(courseService.createCourse(course));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course course) {
    return ResponseEntity.ok(courseService.updateCourse(id, course));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteCourse(@PathVariable Long id) {
    courseService.deleteCourse(id);
    return ResponseEntity.ok().build();
  }
}
