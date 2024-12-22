package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iPrescriptionService;
import com.se100.clinic_management.constants.prescription.PrescriptionStatus;
import com.se100.clinic_management.dto.PrescriptionDetailId;
import com.se100.clinic_management.dto.prescriptions.CreatePrescriptionReq;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.Prescription;
import com.se100.clinic_management.model.PrescriptionDetail;
import com.se100.clinic_management.repository.EmployeeRepository;
import com.se100.clinic_management.repository.ExamRecordRepository;
import com.se100.clinic_management.repository.PrescriptionRepository;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrescriptionService implements iPrescriptionService {
    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ExamRecordRepository examRecordRepository;

    @SneakyThrows
    @Override
    public Prescription createPrescription(CreatePrescriptionReq createPrescriptionReq) {
        Prescription prescription = new Prescription();
        prescription.setServiceRecordId(createPrescriptionReq.getServiceRecordId());
        prescription.setServiceTypeId(createPrescriptionReq.getServiceTypeId());

        //Set pharmacist
        Employee pharmacist = employeeRepository.findById(createPrescriptionReq.getPharmacistId()).orElse(null);

        if (pharmacist == null){
            throw new BaseError("PHARMACIST_NOT_FOUND", "Pharmacist not found", HttpStatus.NOT_FOUND);
        }
        prescription.setPharmacist(pharmacist);

        //Set exam record
        ExamRecord examRecord = examRecordRepository.findById(createPrescriptionReq.getExamRecordId()).orElse(null);

        if (examRecord == null){
            throw new BaseError("EXAM_RECORD_NOT_FOUND", "Exam record not found", HttpStatus.NOT_FOUND);
        }
        prescription.setExamRecord(examRecord);

        //Set prescription detail
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();
        for (CreatePrescriptionReq.PrescriptionDetailDto prescriptionDetailDto : createPrescriptionReq.getPrescriptionDetails()){
           PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
           prescriptionDetail.setMedicineId(prescriptionDetailDto.getMedicineId());
           prescriptionDetail.setDosage(prescriptionDetailDto.getDosage());
           prescriptionDetail.setAmount(prescriptionDetailDto.getAmount());
           prescriptionDetail.setNotes(prescriptionDetail.getNotes());

           prescriptionDetails.add(prescriptionDetail);
        }

        //Set status
        prescription.setStatus(PrescriptionStatus.PENDING);

        //Set createdBy
        prescription.setCreatedBy(pharmacist.getId());

        prescription.setPrescriptionDetails(prescriptionDetails);

        return prescriptionRepository.save(prescription);
    }
}
