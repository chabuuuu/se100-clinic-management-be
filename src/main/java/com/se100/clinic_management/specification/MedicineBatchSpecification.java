package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MedicineBatchSpecification {

  public static Specification<MedicineBatch> filter(
      Integer medicineId,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Boolean isExpired,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Boolean isActive,
      Integer minQuantity) {
    return (Root<MedicineBatch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Lọc theo medicineId
      if (medicineId != null) {
        predicates.add(builder.equal(root.get("medicine").get("id"), medicineId));
      }

      // Lọc theo giá
      if (minPrice != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("price"), minPrice));
      }
      if (maxPrice != null) {
        predicates.add(builder.lessThanOrEqualTo(root.get("price"), maxPrice));
      }

      // Lọc theo ngày hết hạn
      if (isExpired != null) {
        if (isExpired) {
          predicates.add(builder.lessThan(root.get("expireDate"), new Date()));
        } else {
          predicates.add(builder.greaterThanOrEqualTo(root.get("expireDate"), new Date()));
        }
      }

      // Lọc theo ngày nhập (startDate và endDate)
      if (startDate != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("createAt"), startDate));
      }
      if (endDate != null) {
        predicates.add(builder.lessThanOrEqualTo(root.get("createAt"), endDate));
      }

      // Lọc theo trạng thái active (dựa vào trường deleteAt)
      if (isActive != null) {
        if (isActive) {
          predicates.add(builder.isNull(root.get("deleteAt")));
        } else {
          predicates.add(builder.isNotNull(root.get("deleteAt")));
        }
      }

      // Lọc theo số lượng tồn kho
      if (minQuantity != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("quantity"), minQuantity));
      }

      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
