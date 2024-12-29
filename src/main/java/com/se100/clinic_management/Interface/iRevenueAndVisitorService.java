package com.se100.clinic_management.Interface;

import java.time.LocalDate;

public interface iRevenueAndVisitorService {
  Float calculateRevenue(LocalDate startDate, LocalDate endDate);

  int countVisitors(LocalDate startDate, LocalDate endDate);
}
