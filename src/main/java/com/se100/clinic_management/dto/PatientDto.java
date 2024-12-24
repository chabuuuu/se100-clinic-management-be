package com.se100.clinic_management.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {
    private int id;
    private String fullname;
    private String phoneNumber;
    private LocalDate birthday;
    private boolean gender;
}