package com.franktran.enrolmentmanagement.enrolment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnrolmentRepository extends JpaRepository<Enrolment, Long> {

  Optional<Enrolment> findEnrolmentByCourseIdAndStudentIdAndSemester(Long courseId, Long studentId, String semester);

}
