package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.Prescription;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PrescriptionRepository
        extends JpaRepository<Prescription, Integer>, JpaSpecificationExecutor<Prescription> {
    List<Prescription> findByStatusAndCreateAtBetween(String status, LocalDateTime startDate, LocalDateTime endDate);
}
