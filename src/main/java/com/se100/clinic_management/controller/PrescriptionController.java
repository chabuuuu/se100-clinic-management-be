package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iPrescriptionService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.prescriptions.CreatePrescriptionReq;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDto;
import com.se100.clinic_management.dto.prescriptions.UpdatePrescriptionReq;
import com.se100.clinic_management.model.Prescription;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {
    private final iPrescriptionService prescriptionService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO> createPrescription(
            @RequestBody CreatePrescriptionReq createPrescriptionReq) {
        prescriptionService.createPrescription(createPrescriptionReq);
        return ResponseEntityGenerator.createdFormat("Create success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseVO> getPrescriptionById(@PathVariable int id) {
        PrescriptionDetailDto prescription = prescriptionService.getPrescriptionById(id);
        return ResponseEntityGenerator.findOneFormat(prescription);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseVO> updatePrescription(
            @PathVariable int id,
            @RequestBody UpdatePrescriptionReq updatePrescriptionReq) {
        prescriptionService.updatePrescription(id, updatePrescriptionReq);
        return ResponseEntityGenerator.updateFormat("Update success");
    }

    @GetMapping("/")
    public ResponseEntity<Page<PrescriptionDto>> getPrescriptions(
            @RequestParam(required = false) Integer prescriptionId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String pharmacistName,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String fromDate,
            @RequestParam(required = false) String toDate,
            Pageable pageable) {
        Page<PrescriptionDto> prescriptions = prescriptionService.getPrescriptions(prescriptionId, patientName,
                pharmacistName, status, fromDate, toDate, pageable);
        return ResponseEntity.ok(prescriptions);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO> deletePrescription(@PathVariable int id) {
        prescriptionService.deletePrescription(id);
        return ResponseEntityGenerator.deleteFormat("Delete success");
    }
}
