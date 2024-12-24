package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordUpdateReq;
import com.se100.clinic_management.model.ExamRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;

public interface iExamRecordService {
    ExamRecordCreateRes createExamRecord(ExamRecordCreateReq createExamRecordReq);
    int getExamRecordId();
    ExamRecord getExamRecordDetail(int examRecordId);
    void updateExamRecord(ExamRecordUpdateReq examRecord, int examRecordId);

    Page<ExamRecord> getExamRecords(Integer examRecordId, String patientName, String examRoom, Date fromDate, Date toDate, String doctorName, String status, Pageable pageable);

    void deleteExamRecord(int examRecordId);
}
