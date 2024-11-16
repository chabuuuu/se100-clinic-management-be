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
public class ExamRecordCreateReq {
    private Date examDate;

    private Integer numericalOrder;

    private String examRoom;

    private int patientId;

    private int doctorId;
}
