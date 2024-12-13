package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.ServiceType;
import com.se100.clinic_management.model.ServiceTypeEnum;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ServiceTypeSpecification {

  public static Specification<ServiceType> filter(
      String serviceName,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      ServiceTypeEnum type) {
    return (Root<ServiceType> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Lọc theo tên dịch vụ
      if (serviceName != null) {
        predicates.add(builder.like(root.get("serviceName"), "%" + serviceName + "%"));
      }

      // Lọc theo giá dịch vụ
      if (minPrice != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
      }
      if (maxPrice != null) {
        predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
      }

      // Lọc theo type
      if (type != null) {
        predicates.add(builder.equal(root.get("type"), type.toString()));
      }

      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
