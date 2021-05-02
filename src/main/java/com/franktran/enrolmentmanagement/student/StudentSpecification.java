package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.dto.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Objects;

public class StudentSpecification implements Specification<Student> {

  private SearchCriteria criteria;

  public StudentSpecification(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (Objects.isNull(criteria.getValue())) {
      return null;
    }
    if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(root.get(criteria.getKey()), "%" + criteria.getValue() + "%");
      } else {
        return builder.equal(root.get(criteria.getKey()), criteria.getValue());
      }
    }
    return null;
  }
}
