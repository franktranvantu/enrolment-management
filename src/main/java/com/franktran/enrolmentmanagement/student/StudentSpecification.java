package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.dto.SearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class StudentSpecification implements Specification<Student> {

  private SearchCriteria criteria;

  public StudentSpecification(SearchCriteria criteria) {
    this.criteria = criteria;
  }

  @Override
  public Predicate toPredicate(Root<Student> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
    if (StringUtils.isEmpty((CharSequence) criteria.getValue())) {
      return null;
    }
    if (criteria.getOperation().equalsIgnoreCase(":")) {
      if (root.get(criteria.getKey()).getJavaType() == String.class) {
        return builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
      } else {
        return builder.equal(builder.lower(root.get(criteria.getKey())), criteria.getValue().toString().toLowerCase());
      }
    }
    return null;
  }
}
