package com.se100.clinic_management.controller;

import com.se100.clinic_management.service.RevenueAndVisitorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api/statistics")
public class RevenueAndVisitorController {
  @Autowired
  private RevenueAndVisitorServiceImpl revenueAndVisitorService;

  @GetMapping("/revenue")
  public Float getRevenue(
      @RequestParam("startDate") String startDateStr,
      @RequestParam("endDate") String endDateStr) {
    LocalDate startDate = LocalDate.parse(startDateStr);
    LocalDate endDate = LocalDate.parse(endDateStr);
    return revenueAndVisitorService.calculateRevenue(startDate, endDate);
  }

  @GetMapping("/visitors")
  public int getVisitors(
      @RequestParam("startDate") String startDateStr,
      @RequestParam("endDate") String endDateStr) {
    LocalDate startDate = LocalDate.parse(startDateStr);
    LocalDate endDate = LocalDate.parse(endDateStr);
    return revenueAndVisitorService.countVisitors(startDate, endDate);
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<String> handleDateTimeParseException(DateTimeParseException ex) {
    return ResponseEntity.badRequest().body("Ngày không đúng định dạng, vui lòng sử dụng định dạng yyyy-MM-dd.");
  }
}
