package com.se100.clinic_management.controller;

import com.se100.clinic_management.Interface.iTestRecordService;
import com.se100.clinic_management.dto.base_format.ResponseVO;
import com.se100.clinic_management.dto.test_record.TestRecordDto;
import com.se100.clinic_management.model.TestRecord;
import com.se100.clinic_management.utils.ResponseEntityGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/test-records")
@RequiredArgsConstructor
public class TestRecordController {

    @Autowired
    private final iTestRecordService testRecordService;

    @GetMapping("{id}")
    public ResponseEntity<ResponseVO> getTestRecordById(@PathVariable int id) {
        TestRecordDto testRecordDto = testRecordService.getTestRecordById(id);
        return ResponseEntityGenerator.findOneFormat(testRecordDto);
    }

    @GetMapping("")
    public ResponseEntity<Page<TestRecordDto>> getTestRecords(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String patientName,
            @RequestParam(required = false) String technicianName,
            @RequestParam(required = false) String state,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date fromDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date toDate,
            @PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {

        Page<TestRecordDto> testRecordDtos = testRecordService.getTestRecords(id, patientName, technicianName, state,
                fromDate, toDate, pageable);
        return ResponseEntity.ok(testRecordDtos);
    }

    @PostMapping("")
    public ResponseEntity<ResponseVO> createTestRecord(
            @RequestBody TestRecord testRecord) {
        return ResponseEntityGenerator.createdFormat(testRecordService.createTestRecord(testRecord));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseVO> updateTestRecord(
            @PathVariable int id,
            @RequestBody TestRecord testRecord) {
        testRecordService.updateTestRecord(id, testRecord);
        return ResponseEntityGenerator.updateFormat("Update success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseVO> deleteTestRecord(@PathVariable int id) {
        testRecordService.deleteTestRecord(id);
        return ResponseEntityGenerator.deleteFormat(testRecordService.getTestRecordById(id));
    }
}
