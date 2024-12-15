package com.se100.clinic_management.dto.exam_record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamRecordUpdateReq {
    private Date examDate;

    private Integer numericalOrder;

    private String examRoom;

    private int patientId;

    private int doctorId;

    private int serviceTypeId;

    private String symptom;

    private String diagnose;

    private String status;

}
