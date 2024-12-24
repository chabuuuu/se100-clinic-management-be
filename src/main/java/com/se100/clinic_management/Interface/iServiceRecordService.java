package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.ServiceRecordDetailDto;
import com.se100.clinic_management.dto.ServiceRecordDto;
import com.se100.clinic_management.model.ServiceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface iServiceRecordService {
    Page<ServiceRecordDto> getServiceRecords(Integer serviceRecordId, String patientName, String receptionistName, Date fromDate, Date toDate, Pageable pageable);
    ServiceRecordDetailDto getServiceRecordDetail(int serviceRecordId);
}
