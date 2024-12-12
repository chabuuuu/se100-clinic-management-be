package com.se100.clinic_management.Interface;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.se100.clinic_management.model.ServiceRating;

public interface iServiceRatingService {

  ServiceRating getRatingById(int id);

  ServiceRating createRating(ServiceRating rating);

  ServiceRating updateRating(int id, ServiceRating rating);

  void deleteRating(int id);

  Page<ServiceRating> getRatings(
      Integer minScore,
      Integer maxScore,
      Integer patientId,
      LocalDateTime createdAfter,
      LocalDateTime createdBefore,
      Pageable pageable);
}
