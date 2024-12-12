package com.se100.clinic_management.Interface;

import com.se100.clinic_management.model.Medicine;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface iMedicineService {

  // Create a new medicine
  Medicine createMedicine(Medicine medicine);

  // Retrieve a medicine by ID
  Medicine getMedicineById(int id);

  // Update an existing medicine
  Medicine updateMedicine(int id, Medicine medicine);

  // Delete a medicine (soft delete)
  void deleteMedicine(int id);

  // Filter and Pagination
  Page<Medicine> getMedicines(String name,
      String createBy,
      LocalDateTime updatedAfter,
      String ingredient,
      String dosageForm,
      BigDecimal minPrice,
      BigDecimal maxPrice, Pageable pageable);

}
