package com.se100.clinic_management.dto.prescriptions;

import com.se100.clinic_management.dto.BaseRes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionDto extends BaseRes {
    private Integer id;

    private String status;

    private Integer serviceRecordId;

    private Integer serviceTypeId;

    private PrescriptionDetailDto.PharmacistDto pharmacist;

    private PrescriptionDetailDto.PatientDto patient;

    private Integer examRecordId;
}
