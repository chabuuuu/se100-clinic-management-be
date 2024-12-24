package com.se100.clinic_management.dto.exam_record;

import com.se100.clinic_management.dto.BaseRes;
import com.se100.clinic_management.dto.PatientDto;
import com.se100.clinic_management.dto.employee.EmployeeProfileDTO;
import com.se100.clinic_management.model.ServiceType;
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
public class ExamRecordDetailRes extends BaseRes {
    private int id;

    private Date examDate;

    private Integer numericalOrder;

    private String examRoom;

    private String symptom;

    private String diagnose;

    private String status;

    private Integer patientId;

    private Integer doctorId;

    private Integer serviceTypeId;

    private Integer serviceRecordId;

    private PatientDto patient;

    private EmployeeProfileDTO doctor;

    private ServiceType serviceType;
}
