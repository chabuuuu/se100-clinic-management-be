package com.se100.clinic_management.specification;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import com.se100.clinic_management.model.Employee;

public class EmployeeSpecification {

  // Lọc nhân viên theo vai trò
  public static Specification<Employee> hasRole(String role) {
    return (root, query, criteriaBuilder) -> role == null ? criteriaBuilder.conjunction()
        : criteriaBuilder.equal(root.get("role"), role);
  }

  // Lọc nhân viên theo tên
  public static Specification<Employee> fullnameContains(String name) {
    return (root, query, criteriaBuilder) -> name == null ? criteriaBuilder.conjunction()
        : criteriaBuilder.like(root.get("fullname"), "%" + name + "%");
  }

  // Lọc nhân viên theo ngày tạo sau
  public static Specification<Employee> createdAfter(LocalDate date) {
    return (root, query, criteriaBuilder) -> date == null ? criteriaBuilder.conjunction()
        : criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), date);
  }

  // Lọc nhân viên theo ngày tạo trước
  public static Specification<Employee> createdBefore(LocalDate date) {
    return (root, query, criteriaBuilder) -> date == null ? criteriaBuilder.conjunction()
        : criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), date);
  }

  // Tìm kiếm nhân viên theo từ khóa trong tên hoặc email
  public static Specification<Employee> searchKeyword(String keyword) {
    return (root, query, criteriaBuilder) -> {
      if (keyword == null || keyword.trim().isEmpty()) {
        return criteriaBuilder.conjunction();
      }
      String searchPattern = "%" + keyword.trim() + "%";
      return criteriaBuilder.or(
          criteriaBuilder.like(root.get("fullname"), searchPattern),
          criteriaBuilder.like(root.get("email"), searchPattern));
    };
  }

  // Lọc nhân viên chưa bị xóa
  public static Specification<Employee> notDeleted() {
    return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deleteAt"));
  }
}
