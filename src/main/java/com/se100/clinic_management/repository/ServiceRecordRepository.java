package com.se100.clinic_management.repository;

import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.model.ServiceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ServiceRecordRepository
        extends
        JpaRepository<ServiceRecord, Integer>,
        JpaSpecificationExecutor<ServiceRecord>
{
}
