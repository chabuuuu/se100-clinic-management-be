package com.se100.clinic_management.Interface;

import com.se100.clinic_management.model.ServiceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface iServiceRecordService {
    Page<ServiceRecord> getServiceRecords(Integer serviceRecordId, String patientName, String receptionistName, Date fromDate, Date toDate, Pageable pageable);
    ServiceRecord getServiceRecordDetail(int serviceRecordId);
}
