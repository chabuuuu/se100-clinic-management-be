package com.se100.clinic_management.service;

import com.se100.clinic_management.Interface.iPrescriptionService;
import com.se100.clinic_management.constants.prescription.PrescriptionStatus;
import com.se100.clinic_management.dto.JwtTokenVo;
import com.se100.clinic_management.dto.prescriptions.CreatePrescriptionReq;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDto;
import com.se100.clinic_management.dto.prescriptions.UpdatePrescriptionReq;
import com.se100.clinic_management.exception.BaseError;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.Prescription;
import com.se100.clinic_management.model.PrescriptionDetail;
import com.se100.clinic_management.repository.EmployeeRepository;
import com.se100.clinic_management.repository.ExamRecordRepository;
import com.se100.clinic_management.repository.PrescriptionDetailRepository;
import com.se100.clinic_management.repository.PrescriptionRepository;
import com.se100.clinic_management.specification.PrescriptionSpecification;
import com.se100.clinic_management.utils.PageUtil;
import com.se100.clinic_management.utils.SecurityUtil;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    @Autowired
    private PrescriptionDetailRepository prescriptionDetailRepository;

    @SneakyThrows
    @Override
    public Prescription createPrescription(CreatePrescriptionReq createPrescriptionReq) {
        Prescription prescription = new Prescription();
        prescription.setServiceRecordId(createPrescriptionReq.getServiceRecordId());
        prescription.setServiceTypeId(createPrescriptionReq.getServiceTypeId());

        // Set pharmacist
        Employee pharmacist = employeeRepository.findById(createPrescriptionReq.getPharmacistId()).orElse(null);

        if (pharmacist == null) {
            throw new BaseError("PHARMACIST_NOT_FOUND", "Pharmacist not found", HttpStatus.NOT_FOUND);
        }
        prescription.setPharmacist(pharmacist);

        // Set exam record
        if (createPrescriptionReq.getExamRecordId() != null) {
            ExamRecord examRecord = examRecordRepository.findById(createPrescriptionReq.getExamRecordId()).orElse(null);

            if (examRecord == null) {
                throw new BaseError("EXAM_RECORD_NOT_FOUND", "Exam record not found", HttpStatus.NOT_FOUND);
            }
            prescription.setExamRecord(examRecord);
        }

        // Set status
        prescription.setStatus(PrescriptionStatus.PENDING);

        // Set createdBy
        prescription.setCreatedBy(pharmacist.getId());

        Prescription savedPrescription = prescriptionRepository.save(prescription);

        // Set prescription detail
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();
        for (CreatePrescriptionReq.PrescriptionDetailDto prescriptionDetailDto : createPrescriptionReq
                .getPrescriptionDetails()) {
            PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
            prescriptionDetail.setMedicineId(prescriptionDetailDto.getMedicineId());
            prescriptionDetail.setPrescriptionId(savedPrescription.getId());
            prescriptionDetail.setDosage(prescriptionDetailDto.getDosage());
            prescriptionDetail.setAmount(prescriptionDetailDto.getAmount());
            prescriptionDetail.setNotes(prescriptionDetail.getNotes());

            prescriptionDetails.add(prescriptionDetail);
        }

        savedPrescription.setPrescriptionDetails(prescriptionDetails);

        return prescriptionRepository.save(savedPrescription);
    }

    @SneakyThrows
    @Override
    public void updatePrescription(int id, UpdatePrescriptionReq updatePrescriptionReq) {
        Prescription prescription = prescriptionRepository.findById(id).orElse(null);

        if (prescription == null) {
            throw new BaseError("PRESCRIPTION_NOT_FOUND", "Prescription not found", HttpStatus.NOT_FOUND);
        }

        prescription.setServiceRecordId(updatePrescriptionReq.getServiceRecordId());
        prescription.setServiceTypeId(updatePrescriptionReq.getServiceTypeId());

        // Set pharmacist
        Employee pharmacist = employeeRepository.findById(updatePrescriptionReq.getPharmacistId()).orElse(null);

        if (pharmacist == null) {
            throw new BaseError("PHARMACIST_NOT_FOUND", "Pharmacist not found", HttpStatus.NOT_FOUND);
        }
        prescription.setPharmacist(pharmacist);

        // Set exam record
        if (updatePrescriptionReq.getExamRecordId() != null) {
            ExamRecord examRecord = examRecordRepository.findById(updatePrescriptionReq.getExamRecordId()).orElse(null);

            if (examRecord == null) {
                throw new BaseError("EXAM_RECORD_NOT_FOUND", "Exam record not found", HttpStatus.NOT_FOUND);
            }
            prescription.setExamRecord(examRecord);
        }

        // Set prescription detail
        List<PrescriptionDetail> prescriptionDetails = new ArrayList<>();
        for (UpdatePrescriptionReq.PrescriptionDetailDto prescriptionDetailDto : updatePrescriptionReq
                .getPrescriptionDetails()) {
            PrescriptionDetail prescriptionDetail = new PrescriptionDetail();
            prescriptionDetail.setMedicineId(prescriptionDetailDto.getMedicineId());
            prescriptionDetail.setPrescriptionId(prescription.getId());
            prescriptionDetail.setDosage(prescriptionDetailDto.getDosage());
            prescriptionDetail.setAmount(prescriptionDetailDto.getAmount());
            prescriptionDetail.setNotes(prescriptionDetailDto.getNotes());

            prescriptionDetails.add(prescriptionDetail);
        }

        // Set status
        prescription.setStatus(updatePrescriptionReq.getStatus());

        // Set updatedBy
        JwtTokenVo jwtTokenVo = SecurityUtil.getSession();

        prescription.setUpdatedBy(jwtTokenVo.getUserId());

        // Delete old prescription details
        // prescriptionDetailRepository.deleteAll(prescription.getPrescriptionDetails());

        prescription.setPrescriptionDetails(prescriptionDetails);

        prescriptionRepository.save(prescription);
    }

    @Override
    public PrescriptionDetailDto getPrescriptionById(int id) {
        Prescription prescription = prescriptionRepository.findById(id).orElse(null);

        if (prescription == null) {
            return null;
        }

        PrescriptionDetailDto prescriptionDetailDto = new PrescriptionDetailDto();
        prescriptionDetailDto.setId(prescription.getId());
        prescriptionDetailDto.setStatus(prescription.getStatus());
        prescriptionDetailDto.setServiceRecordId(prescription.getServiceRecordId());
        prescriptionDetailDto.setServiceType(prescription.getServiceType());

        PrescriptionDetailDto.PharmacistDto pharmacistDto = new PrescriptionDetailDto.PharmacistDto();

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.map(prescription.getPharmacist(), pharmacistDto);

        prescriptionDetailDto.setPharmacist(pharmacistDto);

        if (prescription.getExamRecord() != null) {
            prescriptionDetailDto.setExamRecordId(prescription.getExamRecord().getId());
        }

        List<PrescriptionDetailDto.MedicineDetailDto> medicineDetailDtos = new ArrayList<>();

        for (PrescriptionDetail prescriptionDetail : prescription.getPrescriptionDetails()) {
            PrescriptionDetailDto.MedicineDetailDto detailDto = new PrescriptionDetailDto.MedicineDetailDto();
            detailDto.setMedicine(prescriptionDetail.getMedicine());
            detailDto.setDosage(prescriptionDetail.getDosage());
            detailDto.setAmount(prescriptionDetail.getAmount());
            detailDto.setNotes(prescriptionDetail.getNotes());

            medicineDetailDtos.add(detailDto);
        }

        prescriptionDetailDto.setPrescriptionDetails(medicineDetailDtos);

        // Set patient
        PrescriptionDetailDto.PatientDto patientDto = new PrescriptionDetailDto.PatientDto();
        modelMapper.map(prescription.getServiceRecord().getPatient(), patientDto);
        prescriptionDetailDto.setPatient(patientDto);

        // Base entity
        prescriptionDetailDto.setCreateAt(prescription.getCreateAt());
        prescriptionDetailDto.setUpdateAt(prescription.getUpdateAt());
        prescriptionDetailDto.setCreatedBy(prescription.getCreatedBy());
        prescriptionDetailDto.setUpdatedBy(prescription.getUpdatedBy());

        return prescriptionDetailDto;
    }

    @Override
    public Page<PrescriptionDto> getPrescriptions(Integer prescriptionId, String patientName, String pharmacistName,
            String status, String fromDate, String toDate, Pageable pageable) {
        Specification<Prescription> specification = PrescriptionSpecification.filter(prescriptionId, patientName,
                pharmacistName, status, fromDate, toDate);

        Page<Prescription> prescriptionPage = prescriptionRepository.findAll(specification, pageable);

        // Quick convert from Page<Prescription> to Page<PrescriptionDto>
        Page<PrescriptionDto> prescriptionDtoPage = prescriptionPage.map(prescription -> {
            PrescriptionDto prescriptionDto = new PrescriptionDto();
            prescriptionDto.setId(prescription.getId());
            prescriptionDto.setStatus(prescription.getStatus());
            prescriptionDto.setServiceRecordId(prescription.getServiceRecordId());
            prescriptionDto.setServiceTypeId(prescription.getServiceTypeId());

            PrescriptionDetailDto.PharmacistDto pharmacistDto = new PrescriptionDetailDto.PharmacistDto();

            ModelMapper modelMapper = new ModelMapper();

            modelMapper.map(prescription.getPharmacist(), pharmacistDto);

            prescriptionDto.setPharmacist(pharmacistDto);

            if (prescription.getExamRecord() != null) {
                prescriptionDto.setExamRecordId(prescription.getExamRecord().getId());
            }

            // Set patient
            PrescriptionDetailDto.PatientDto patientDto = new PrescriptionDetailDto.PatientDto();
            modelMapper.map(prescription.getServiceRecord().getPatient(), patientDto);
            prescriptionDto.setPatient(patientDto);

            return prescriptionDto;
        });

        return prescriptionDtoPage;
    }

    @Override
    public void deletePrescription(int id) {
        // SET deleteAt = current time
        Prescription prescription = prescriptionRepository.findById(id).orElse(null);
        if (prescription != null) {
            prescription.setDeleteAt(java.time.LocalDateTime.now());
            prescriptionRepository.save(prescription);
        }
    }
}
