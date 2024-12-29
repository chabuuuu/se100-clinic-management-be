package com.se100.clinic_management.specification;

import com.se100.clinic_management.model.Invoice;
import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.domain.Specification;

import java.util.Date;

public class InvoiceSpecification {
    public static Specification<Invoice> filter(String patientName, String receptionistName, Date fromDate, Date toDate) {

        return (root, query, builder) -> {
            return builder.and(
                    patientName == null ? builder.conjunction() : builder.like(root.get("patient").get("fullname"), "%" + patientName + "%"),
                    receptionistName == null ? builder.conjunction() : builder.like(root.get("receptionist").get("fullname"), "%" + receptionistName + "%"),
                    fromDate == null ? builder.conjunction() : builder.greaterThanOrEqualTo(root.get("createAt"), fromDate),
                    toDate == null ? builder.conjunction() : builder.lessThanOrEqualTo(root.get("createAt"), toDate),

                    //deleteAt is null
                    builder.isNull(root.get("deleteAt"))
            );
        };
    }
}