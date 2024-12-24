package com.se100.clinic_management.dto.test_record;

import com.se100.clinic_management.dto.PatientDto;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.dto.prescriptions.PrescriptionDetailDto;
import com.se100.clinic_management.model.Employee;
import com.se100.clinic_management.model.Patient;
import com.se100.clinic_management.model.ServiceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestRecordDto {
    private Integer id;

    private Date testDate;

    private Integer numericalOrder;

    private List<String> testArtachments;

    private String diagnose;

    private String state;

    private ServiceType serviceType;

    private Integer serviceRecordId;

    private PatientDto patient;

    private EmployeeProfileDTO technician;
}