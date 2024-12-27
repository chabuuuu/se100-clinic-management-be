package com.se100.clinic_management.dto.ratings;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceRatingDto {
  private int id;
  private int score;
  private PatientDto patient;
  private ServiceRecordDto serviceRecord;
  private LocalDateTime createAt;
  private LocalDateTime updateAt;
  private LocalDateTime deleteAt;
}
