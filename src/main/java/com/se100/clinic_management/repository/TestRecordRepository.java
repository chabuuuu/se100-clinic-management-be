package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.TestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TestRecordRepository extends JpaRepository<TestRecord, Integer>, JpaSpecificationExecutor<TestRecord> {
}
