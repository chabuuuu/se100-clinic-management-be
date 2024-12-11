package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer>, JpaSpecificationExecutor<Medicine> {
  Medicine findByName(String name);
}
