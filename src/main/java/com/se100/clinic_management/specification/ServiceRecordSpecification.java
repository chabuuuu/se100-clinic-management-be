package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.ServiceRecord;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ServiceRecordSpecification {
    public static Specification<ServiceRecord> filter(
            Integer serviceRecordId,
            String patientName,
            String receptionistName,
            Date fromDate,
            Date toDate
    ) {
        return (root, query, builder) -> {
            return builder.and(
                    serviceRecordId == null ? builder.conjunction() : builder.equal(root.get("id"), serviceRecordId),
                    patientName == null ? builder.conjunction() : builder.like(root.get("patient").get("fullname"), "%" + patientName + "%"),
                    receptionistName == null ? builder.conjunction() : builder.like(root.get("receptionist").get("fullname"), "%" + receptionistName + "%"),
                    fromDate == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("createAt"), fromDate),
                    toDate == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("createAt"), toDate)
            );
        };
    }

}
