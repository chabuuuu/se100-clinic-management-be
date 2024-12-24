package com.se100.clinic_management.dto;

import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.dto.test_record.TestRecordDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceRecordDto {
    private int id;

    private String description;

    private PatientDto patient;

    private EmployeeProfileDTO receptionist;
}
