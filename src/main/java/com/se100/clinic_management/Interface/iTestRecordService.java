package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.test_record.TestRecordDto;
import com.se100.clinic_management.model.TestRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface iTestRecordService {
    TestRecordDto createTestRecord(TestRecord testRecordDto);
    void updateTestRecord(int id, TestRecord testRecordDto);
    void deleteTestRecord(int id);
    TestRecordDto getTestRecordById(int id);
    Page<TestRecordDto> getTestRecords(Integer id, String patientName, String technicianName, String state, Date fromDate, Date toDate, Pageable pageable);
}
