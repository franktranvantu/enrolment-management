package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.dto.DateRange;
import com.franktran.enrolmentmanagement.util.CriteriaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

  public static Specification<Student> hasNameLike(String name) {
    if (StringUtils.isBlank(name)) {
      return null;
    }
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Student.Fields.name)), CriteriaUtils.formatLike(name));
  }

  public static Specification<Student> hasEmailLike(String email) {
    if (StringUtils.isBlank(email)) {
      return null;
    }
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Student.Fields.email)), CriteriaUtils.formatLike(email));
  }

  public static Specification<Student> hasDateRangeBetween(DateRange dateRange) {
    if (dateRange == null) {
      return null;
    }
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.between(root.get(Student.Fields.dob), dateRange.getFrom(), dateRange.getTo());
  }

}
