package com.franktran.enrolmentmanagement.student;

import com.franktran.enrolmentmanagement.dto.DateRange;
import com.franktran.enrolmentmanagement.dto.SearchCriteria;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
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
    if (root.get(criteria.getKey()).getJavaType() == String.class && StringUtils.isNoneEmpty(criteria.getKey())) {
      return builder.like(builder.lower(root.get(criteria.getKey())), "%" + criteria.getValue().toString().toLowerCase() + "%");
    } else if (root.get(criteria.getKey()).getJavaType() == LocalDate.class) {
      DateRange dateRange = (DateRange) criteria.getValue();
      return builder.between(root.get(criteria.getKey()), dateRange.getFrom(), dateRange.getTo());
    }
    return null;
  }
}
