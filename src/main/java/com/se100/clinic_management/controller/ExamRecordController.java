package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iExamRecordService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.exam_record.ExamRecordCreateReq;
import com.se100.clinic_management.dto.exam_record.ExamRecordUpdateReq;
import com.se100.clinic_management.model.ExamRecord;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/exam-records")
@RequiredArgsConstructor
public class ExamRecordController {

    @Autowired
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

    @PutMapping("/update/{examRecordId}")
    public ResponseEntity<ResponseVO> update(@RequestBody ExamRecordUpdateReq examRecordUpdateReq, @PathVariable int examRecordId) {
        examRecordService.updateExamRecord(examRecordUpdateReq, examRecordId);
        return ResponseEntityGenerator.okFormat("Update successfully");
    }

    @GetMapping("")
    public ResponseEntity<Page<ExamRecord>> getExamRecords(
            @RequestParam(required = false) Integer examRecordId,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String examRoom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @RequestParam(required = false) String doctorName,
            @RequestParam(required = false) String status,
            Pageable pageable
    ) {
        Page<ExamRecord> examRecords = examRecordService.getExamRecords(examRecordId, patientName, examRoom, fromDate, toDate, doctorName, status, pageable);
        return ResponseEntity.ok(examRecords);
    }

    @DeleteMapping("/{examRecordId}")
    public ResponseEntity<ResponseVO> deleteExamRecord(@PathVariable int examRecordId) {
        examRecordService.deleteExamRecord(examRecordId);
        return ResponseEntityGenerator.okFormat("Delete successfully");
    }
}
