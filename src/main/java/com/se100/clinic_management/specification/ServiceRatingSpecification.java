package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.ServiceRating;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServiceRatingSpecification {

  public static Specification<ServiceRating> filter(
      Integer minScore,
      Integer maxScore,
      Integer patientId,
      LocalDateTime createdAfter,
      LocalDateTime createdBefore) {

    return (Root<ServiceRating> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Lọc theo score
      if (minScore != null && minScore != 0) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("score"), minScore));
      }

      if (maxScore != null && maxScore != 0) {
        predicates.add(builder.lessThanOrEqualTo(root.get("score"), maxScore));
      }

      // Lọc theo patientId
      if (patientId != null && patientId != 0) {
        predicates.add(builder.equal(root.get("patient").get("id"), patientId));
      }

      // Lọc theo serviceRecordId
      // if (serviceRecordId != 0) {
      // predicates.add(builder.equal(root.get("serviceRecord").get("id"),
      // serviceRecordId));
      // }

      // Lọc theo ngày tạo (createdAfter và createdBefore)
      if (createdAfter != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("createAt"), createdAfter));
      }

      if (createdBefore != null) {
        predicates.add(builder.lessThanOrEqualTo(root.get("createAt"), createdBefore));
      }

      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
