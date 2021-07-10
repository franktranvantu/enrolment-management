package com.franktran.enrolmentmanagement.course;

import com.franktran.enrolmentmanagement.dto.SearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class CourseSpecification implements Specification<Course> {

  private SearchCriteria criteria;

  public CourseSpecification(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (Objects.isNull(criteria.getValue())) {
      return null;
    }
    if (root.get(criteria.getKey()).getJavaType() == String.class && StringUtils.isNoneEmpty(criteria.getKey())) {
      return builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
    }
    return null;
  }
}
