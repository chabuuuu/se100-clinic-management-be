package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.Prescription;
import org.springframework.data.jpa.domain.Specification;

public class PrescriptionSpecification {
    public static Specification<Prescription> filter(
            Integer prescriptionId,
            String patientName,
            String pharmacistName,
            String status,
            String fromDate,
            String toDate
    ) {
        return (root, query, builder) -> {
            return builder.and(
                    prescriptionId == null ? builder.conjunction() : builder.equal(root.get("id"), prescriptionId),

                    //Get patient name by join to serviceRecord, then get patient
                    patientName == null ? builder.conjunction() : builder.like(root.get("serviceRecord").get("patient").get("fullname"), "%" + patientName + "%"),
                    pharmacistName == null ? builder.conjunction() : builder.like(root.get("pharmacist").get("fullname"), "%" + pharmacistName + "%"),
                    status == null ? builder.conjunction() : builder.equal(root.get("status"), status),
                    fromDate == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("createAt"), fromDate),
                    toDate == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("createAt"), toDate),

                    //deleteAt is null
                    builder.isNull(root.get("deleteAt"))
            );
        };
    }
}
