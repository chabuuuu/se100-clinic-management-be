package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iExamRecordService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/exam-records")
@RequiredArgsConstructor
public class ExamRecordController {
    private final iExamRecordService examRecordService;

    @PostMapping("/create")
    public ResponseEntity<ResponseVO> create(@RequestBody ExamRecordCreateReq examRecordCreateReq) {
        var result = examRecordService.createExamRecord(examRecordCreateReq);
        return ResponseEntityGenerator.okFormat(result);
    }

    @GetMapping("/preview-id")
    public ResponseEntity<ResponseVO> previewId() {
        var result = examRecordService.getExamRecordId();
        return ResponseEntityGenerator.okFormat(result);
    }

    @GetMapping("/detail/{examRecordId}")
    public ResponseEntity<ResponseVO> getDetail(@PathVariable int examRecordId) {
        var result = examRecordService.getExamRecordDetail(examRecordId);
        return ResponseEntityGenerator.okFormat(result);
    }
}
