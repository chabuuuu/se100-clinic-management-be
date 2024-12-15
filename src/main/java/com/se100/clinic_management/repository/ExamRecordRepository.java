package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.MedicineBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Integer>, JpaSpecificationExecutor<ExamRecord> {
}
