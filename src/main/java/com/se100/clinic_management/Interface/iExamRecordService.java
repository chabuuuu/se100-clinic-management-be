package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;

public interface iExamRecordService {
    ExamRecordCreateRes createExamRecord(ExamRecordCreateReq createExamRecordReq);
    int getExamRecordId();
    ExamRecordDetailRes getExamRecordDetail(int examRecordId);
}
