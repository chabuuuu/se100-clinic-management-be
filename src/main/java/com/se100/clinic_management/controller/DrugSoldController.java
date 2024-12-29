package com.se100.clinic_management.controller;

import com.se100.clinic_management.service.DrugSoldServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/drugs")
public class DrugSoldController {
  @Autowired
  private DrugSoldServiceImpl drugSoldService;

  @GetMapping("/sold")
  public int getDrugsSold(
      @RequestParam("startDate") String startDateStr,
      @RequestParam("endDate") String endDateStr) {
    // Parse ngày từ chuỗi (chỉ ngày, tháng, năm)
    LocalDate startDate = LocalDate.parse(startDateStr);
    LocalDate endDate = LocalDate.parse(endDateStr);

    // Chuyển đổi LocalDate sang LocalDateTime để tính toán
    LocalDateTime startDateTime = startDate.atStartOfDay();
    LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

    // Gọi service để lấy tổng số lượng thuốc bán ra
    return drugSoldService.getTotalDrugsSold(startDateTime, endDateTime);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
    return ResponseEntity.badRequest().body("Ngày không đúng định dạng, vui lòng sử dụng định dạng yyyy-MM-dd.");
  }

}
