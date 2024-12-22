package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iPrescriptionService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.prescriptions.CreatePrescriptionReq;
import com.se100.clinic_management.model.Prescription;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final iPrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO> createPrescription(
            @RequestBody CreatePrescriptionReq createPrescriptionReq
            ) {
        Prescription prescription = prescriptionService.createPrescription(createPrescriptionReq);
        return ResponseEntityGenerator.createdFormat(prescription);
    }
}
