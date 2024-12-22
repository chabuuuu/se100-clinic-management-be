package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.prescriptions.CreatePrescriptionReq;
import com.se100.clinic_management.model.Prescription;

public interface iPrescriptionService {
    Prescription createPrescription(CreatePrescriptionReq createPrescriptionReq);
}
