package com.se100.clinic_management.Interface;

import java.time.LocalDateTime;

public interface iDrugSoldService {
  int getTotalDrugsSold(LocalDateTime startDate, LocalDateTime endDate);
}
