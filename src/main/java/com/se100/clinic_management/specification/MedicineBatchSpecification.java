package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicineBatchSpecification {

  public static Specification<MedicineBatch> filter(
      Integer medicineId,
      double minPrice,
      double maxPrice,
      boolean isExpired) {
    return (Root<MedicineBatch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (medicineId != null) {
        predicates.add(builder.equal(root.get("medicine").get("id"), medicineId));
      }

      if (minPrice > 0) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
      }

      if (maxPrice > 0) {
        predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
      }

      if (isExpired) {
        predicates.add(builder.lessThan(root.get("expireDate"), new Date()));
      }

      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
