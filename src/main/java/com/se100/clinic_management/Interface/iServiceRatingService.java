package com.se100.clinic_management.Interface;

import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.se100.clinic_management.dto.ratings.ServiceRatingDto;
import com.se100.clinic_management.model.ServiceRating;

public interface iServiceRatingService {

  // Trả về Dto thay vì entity
  ServiceRatingDto getRatingById(int id);

  ServiceRating createRating(ServiceRating rating);

  ServiceRatingDto updateRating(int id, ServiceRating rating);

  void deleteRating(int id);

  // Trả về Page chứa Dto thay vì entity
  Page<ServiceRatingDto> getRatings(
      Integer minScore,
      Integer maxScore,
      Integer patientId,
      LocalDateTime createdAfter,
      LocalDateTime createdBefore,
      Pageable pageable);
}
