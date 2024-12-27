package com.se100.clinic_management.controller;

import com.se100.clinic_management.model.ServiceRating;
import com.se100.clinic_management.service.ServiceRatingServiceImpl;
import com.se100.clinic_management.utils.ResponseEntityGenerator;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.se100.clinic_management.dto.base_format.ResponseVO;

@RestController
@RequestMapping("/api/service-ratings")
public class ServiceRatingController {

  @Autowired
  private ServiceRatingServiceImpl serviceRatingService;

  // Get service rating by ID
  @GetMapping("/{id}")
  public ResponseEntity<ResponseVO> getRatingById(@PathVariable int id) {
    ServiceRating rating = serviceRatingService.getRatingById(id);
    return ResponseEntityGenerator.findOneFormat(rating);
  }

  // Create new service rating
  @PostMapping
  public ResponseEntity<ResponseVO> createRating(@RequestBody ServiceRating rating) {
    ServiceRating createdRating = serviceRatingService.createRating(rating);
    return ResponseEntityGenerator.createdFormat(createdRating);
  }

  // Update an existing service rating
  @PutMapping("/{id}")
  public ResponseEntity<ResponseVO> updateRating(@PathVariable int id, @RequestBody ServiceRating rating) {
    ServiceRating updatedRating = serviceRatingService.updateRating(id, rating);
    return ResponseEntityGenerator.updateFormat(updatedRating);
  }

  // Delete a service rating
  @DeleteMapping("/{id}")
  public ResponseEntity<ResponseVO> deleteRating(@PathVariable int id) {
    serviceRatingService.deleteRating(id);
    return ResponseEntityGenerator.deleteFormat("Service rating deleted successfully");
  }

  // Get service ratings with filters
  @GetMapping
  public ResponseEntity<Page<ServiceRating>> getRatings(
      @RequestParam(required = false) Integer minScore,
      @RequestParam(required = false) Integer maxScore,
      @RequestParam(required = false) Integer patientId,
      @RequestParam(required = false) LocalDateTime createdAfter,
      @RequestParam(required = false) LocalDateTime createdBefore,
      Pageable pageable) {
    Page<ServiceRating> ratings = serviceRatingService.getRatings(minScore, maxScore, patientId,
        createdAfter, createdBefore, pageable);
    return new ResponseEntity<>(ratings, HttpStatus.OK);
  }

}
