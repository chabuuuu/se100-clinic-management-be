package com.se100.clinic_management.dto;

import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.dto.test_record.TestRecordDto;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRecordDetailDto {

    private int id;

    private String description;

    private PatientDto patient;

    private EmployeeProfileDTO receptionist;

    private List<TestRecordDto> testRecords;

    private List<PrescriptionDetailDto> prescriptions;

    private List<ExamRecordDetailRes> examRecords;

    private Float examTotal;

    private Float testTotal;

    private Float prescriptionTotal;

    private Float total;
}
