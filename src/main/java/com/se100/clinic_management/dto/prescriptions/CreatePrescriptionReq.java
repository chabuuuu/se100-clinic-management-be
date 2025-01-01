package com.se100.clinic_management.dto.prescriptions;

import com.se100.clinic_management.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrescriptionReq {
    private Integer serviceRecordId;

    private Integer serviceTypeId;

    private Integer pharmacistId;

    private Integer examRecordId;

    private Integer patientId;

    private List<PrescriptionDetailDto> prescriptionDetails;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrescriptionDetailDto {
        private Integer medicineId;

        private Integer amount;

        private String notes;

        private String dosage; // Liều lượng
    }
}
