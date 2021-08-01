package com.franktran.enrolmentmanagement.course;

import com.franktran.enrolmentmanagement.util.CriteriaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CourseSpecification {

  public static Specification<Course> hasNameLike(String name) {
    if (StringUtils.isEmpty(name)) {
      return null;
    }
    return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(criteriaBuilder.lower(root.get(Course.Fields.name)), CriteriaUtils.formatLike(name));
  }

}
