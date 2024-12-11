package com.se100.clinic_management.Interface;

import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

public interface iMedicineBatchService {

  MedicineBatch getMedicineBatchById(int id);

  MedicineBatch createMedicineBatch(MedicineBatch medicineBatch);

  MedicineBatch updateMedicineBatch(int id, MedicineBatch medicineBatch);

  void deleteMedicineBatch(int id);

  Page<MedicineBatch> getMedicineBatches(int medicineId, boolean isExpired, double minPrice, double maxPrice,
      Pageable pageable);
}
