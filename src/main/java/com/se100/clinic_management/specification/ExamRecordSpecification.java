package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class ExamRecordSpecification {
    public static Specification<ExamRecord> filter(
            Integer examRecordId,
            String patientName,
            String examRoom,
            String doctorName,
            String status,
            Date fromDate,
            Date toDate
    ) {
        return (root, query, builder) -> {
            return builder.and(
                    examRecordId == null ? builder.conjunction() : builder.equal(root.get("id"), examRecordId),
                    patientName == null ? builder.conjunction() : builder.like(root.get("patient").get("fullname"), "%" + patientName + "%"),
                    examRoom == null ? builder.conjunction() : builder.like(root.get("examRoom"), "%" + examRoom + "%"),
                    doctorName == null ? builder.conjunction() : builder.like(root.get("doctor").get("fullname"), "%" + doctorName + "%"),
                    status == null ? builder.conjunction() : builder.equal(root.get("status"), status),
                    fromDate == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("createAt"), fromDate),
                    toDate == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("createAt"), toDate),
                    builder.equal(root.get("deleteAt"), null)
            );
        };
    }
}
