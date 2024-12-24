package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.prescriptions.CreatePrescriptionReq;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDto;
import com.se100.clinic_management.dto.prescriptions.UpdatePrescriptionReq;
import com.se100.clinic_management.model.Prescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface iPrescriptionService {
    Prescription createPrescription(CreatePrescriptionReq createPrescriptionReq);
    void updatePrescription(int id, UpdatePrescriptionReq updatePrescriptionReq);
    PrescriptionDetailDto getPrescriptionById(int id);
    Page<PrescriptionDto> getPrescriptions(Integer prescriptionId, String patientName, String pharmacistName, String status, String fromDate, String toDate, Pageable pageable);
    void deletePrescription(int id);
}
