package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicineBatchSpecification {

  public static Specification<MedicineBatch> filter(
      Integer medicineId,
      Boolean isExpired,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Boolean isActive) {

    return (Root<MedicineBatch> root, CriteriaQuery<?> query, CriteriaBuilder builder) -> {
      List<Predicate> predicates = new ArrayList<>();

      // Lọc theo medicineId
      if (medicineId != null) {
        predicates.add(builder.equal(root.get("medicine").get("id"), medicineId));
      }

      // Lọc theo ngày hết hạn (expireDate)
      if (isExpired != null) {
        LocalDateTime now = LocalDateTime.now();
        if (isExpired) {
          predicates.add(builder.lessThan(root.get("expireDate"), now));
        } else {
          predicates.add(builder.greaterThanOrEqualTo(root.get("expireDate"), now));
        }
      }

      // Lọc theo ngày tạo (createAt) trong khoảng startDate và endDate
      if (startDate != null) {
        predicates.add(builder.greaterThanOrEqualTo(root.get("createAt"), startDate));
      }
      if (endDate != null) {
        predicates.add(builder.lessThanOrEqualTo(root.get("createAt"), endDate));
      }

      // Thêm logic mặc định: loại bỏ bản ghi có `deleteAt != NULL` nếu `isActive !=
      // false`
      if (isActive == null || isActive) {
        predicates.add(builder.isNull(root.get("deleteAt"))); // Chỉ lấy bản ghi chưa bị xóa
      } else {
        predicates.add(builder.isNotNull(root.get("deleteAt"))); // Chỉ lấy bản ghi đã bị xóa
      }

      // Trả về tất cả điều kiện kết hợp bằng `AND`
      return builder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
