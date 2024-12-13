package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineBatchRepository
    extends JpaRepository<MedicineBatch, Integer>, JpaSpecificationExecutor<MedicineBatch> {
}
