package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.ServiceType;
import com.se100.clinic_management.model.TestRecord;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class TestRecordSpecification {
    public static Specification<TestRecord> filter(
            Integer id,
            String patientName,
            String technicianName,
            String state,
            Date fromDate,
            Date toDate
            ) {
        return (root, query, builder) -> {
            return builder.and(
                    id == null ? builder.conjunction() : builder.equal(root.get("id"), id),
                    patientName == null ? builder.conjunction() : builder.like(root.get("patient").get("fullname"), "%" + patientName + "%"),
                    technicianName == null ? builder.conjunction() : builder.like(root.get("technician").get("fullname"), "%" + technicianName + "%"),
                    state == null ? builder.conjunction() : builder.equal(root.get("state"), state),
                    fromDate == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("testDate"), fromDate),
                    toDate == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("testDate"), toDate),

                    //deleteAt is null
                    builder.isNull(root.get("deleteAt"))
            );
        };
    }
}
