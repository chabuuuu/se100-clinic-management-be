package com.se100.clinic_management.Interface;

import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;

public interface iMedicineBatchService {

  MedicineBatch getMedicineBatchById(int id);

  MedicineBatch createMedicineBatch(MedicineBatch medicineBatch);

  MedicineBatch updateMedicineBatch(int id, MedicineBatch medicineBatch);

  void deleteMedicineBatch(int id);

  Page<MedicineBatch> getMedicineBatches(Integer medicineId,
      BigDecimal minPrice,
      BigDecimal maxPrice,
      Boolean isExpired,
      LocalDateTime startDate,
      LocalDateTime endDate,
      Boolean isActive,
      Integer minQuantity,
      Pageable pageable);
}
