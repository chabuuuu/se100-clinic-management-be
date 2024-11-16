package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.ExamRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRecordRepository extends JpaRepository<ExamRecord, Integer> {
}
