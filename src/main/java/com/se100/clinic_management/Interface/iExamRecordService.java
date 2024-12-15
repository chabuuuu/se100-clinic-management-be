package com.se100.clinic_management.Interface;

import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordDetailRes;
import com.se100.clinic_management.dto.exam_record.ExamRecordUpdateReq;
import com.se100.clinic_management.model.ExamRecord;

public interface iExamRecordService {
    ExamRecordCreateRes createExamRecord(ExamRecordCreateReq createExamRecordReq);
    int getExamRecordId();
    ExamRecord getExamRecordDetail(int examRecordId);
    void updateExamRecord(ExamRecordUpdateReq examRecord, int examRecordId);
}
