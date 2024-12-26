package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.Patient;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PatientSpecification {

    // Lọc bệnh nhân theo tên
    public static Specification<Patient> fullnameContains(String name) {
        return (root, query, criteriaBuilder) -> name == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("fullname"), "%" + name + "%");
    }

    // Lọc bệnh nhân theo giới tính
    public static Specification<Patient> hasGender(Boolean gender) {
        return (root, query, criteriaBuilder) -> gender == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("gender"), gender);
    }

    // Lọc bệnh nhân theo số điện thoại
    public static Specification<Patient> hasPhoneNumber(String phoneNumber) {
        return (root, query, criteriaBuilder) -> phoneNumber == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(root.get("phoneNumber"), "%" + phoneNumber + "%");
    }

    // Lọc bệnh nhân theo ngày tạo sau
    public static Specification<Patient> createdAfter(LocalDate date) {
        return (root, query, criteriaBuilder) -> date == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.greaterThanOrEqualTo(root.get("createAt"), date);
    }

    // Lọc bệnh nhân theo ngày tạo trước
    public static Specification<Patient> createdBefore(LocalDate date) {
        return (root, query, criteriaBuilder) -> date == null ? criteriaBuilder.conjunction()
                : criteriaBuilder.lessThanOrEqualTo(root.get("createAt"), date);
    }

    // Lọc bệnh nhân theo độ tuổi lớn hơn hoặc bằng minAge
    public static Specification<Patient> hasMinAge(Integer minAge) {
        return (root, query, criteriaBuilder) -> {
            if (minAge == null || minAge <= 0) {
                return criteriaBuilder.conjunction(); // Không lọc nếu tuổi không hợp lệ
            }
            LocalDate minDate = LocalDate.now().minusYears(minAge);
            return criteriaBuilder.lessThanOrEqualTo(root.get("birthday"), minDate);
        };
    }

    // Lọc bệnh nhân theo độ tuổi nhỏ hơn hoặc bằng maxAge
    public static Specification<Patient> hasMaxAge(Integer maxAge) {
        return (root, query, criteriaBuilder) -> {
            if (maxAge == null || maxAge <= 0) {
                return criteriaBuilder.conjunction(); // Không lọc nếu tuổi không hợp lệ
            }
            LocalDate maxDate = LocalDate.now().minusYears(maxAge);
            return criteriaBuilder.greaterThanOrEqualTo(root.get("birthday"), maxDate);
        };
    }

    // Tìm kiếm bệnh nhân theo từ khóa trong tên hoặc số trong số điện thoại
    public static Specification<Patient> searchKeyword(String keyword) {
        return (root, query, criteriaBuilder) -> {
            if (keyword == null || keyword.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            String searchPattern = "%" + keyword.trim() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("fullname"), searchPattern),
                    criteriaBuilder.like(root.get("phoneNumber"), searchPattern));
        };
    }

    public static Specification<Patient> isNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deleteAt"));
    }

}
