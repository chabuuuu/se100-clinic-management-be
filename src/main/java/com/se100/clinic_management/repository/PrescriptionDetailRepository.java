package com.se100.clinic_management.repository;

import com.se100.clinic_management.dto.PrescriptionDetailId;
import com.se100.clinic_management.model.PrescriptionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionDetailRepository extends JpaRepository<PrescriptionDetail, PrescriptionDetailId> {
}
