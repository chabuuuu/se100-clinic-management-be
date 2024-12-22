package com.se100.clinic_management.dto.prescriptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePrescriptionReq extends CreatePrescriptionReq{
    private String status;
}
