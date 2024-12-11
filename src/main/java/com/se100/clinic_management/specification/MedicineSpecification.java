package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.Medicine;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

public class MedicineSpecification {

  public static Specification<Medicine> filter(
      String name,
      String createBy,
      LocalDateTime updatedAfter,
      String ingredient,
      String dosageForm,
      double minPrice,
      double maxPrice) {
    return (Root<Medicine> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (name != null) {
        predicates.add(builder.like(root.get("name"), "%" + name + "%"));
      }

      if (createBy != null) {
        predicates.add(builder.equal(root.get("createBy"), createBy));
      }

      if (updatedAfter != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("updateAt"), updatedAfter));
      }

      if (ingredient != null) {
        predicates.add(builder.like(root.get("ingredient"), "%" + ingredient + "%"));
      }

      if (dosageForm != null) {
        predicates.add(builder.like(root.get("dosageForm"), "%" + dosageForm + "%"));
      }

      if (minPrice > 0) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
      }

      if (maxPrice > 0) {
        predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
      }

      predicates.add(builder.isNull(root.get("deleteAt")));

      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
