package com.se100.clinic_management.dto.service_record;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateServiceRecordReq {
    private String description;
    private int patientId;
    private int receptionistId;
    private LocalDateTime createAt;
}
